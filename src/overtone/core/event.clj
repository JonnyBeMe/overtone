(ns
  ^{:doc "A simple event system that processes fired events in a thread pool."
     :author "Jeff Rose"}
  overtone.core.event
  (:import (java.util.concurrent Executors LinkedBlockingQueue))
  (:require [overtone.core.log :as log])
  (:use (overtone.core util)
        clojure.stacktrace
        [clojure.set :only [intersection difference]]))

(def NUM-THREADS (cpu-count))
(defonce thread-pool (Executors/newFixedThreadPool NUM-THREADS))
(defonce event-handlers* (ref {}))

(defn on
  "Runs handler whenever events of type event-type are fired.  The handler can
  optionally except a single event argument, which is a map containing the
  :event-type property and any other properties specified when it was fired.

  (on ::booted #(do-stuff))
  (on ::midi-note-down (fn [event] (funky-bass (:note event))))

  Handlers can return :done to be removed from the handler list after execution."
  [event-type handler]
  (log/debug "adding-handler for " event-type)
  (dosync
    (let [handlers (get @event-handlers* event-type #{})]
      (alter event-handlers* assoc event-type (conj handlers handler))
      true)))

(defn remove-handler
  "Remove an event handler previously registered to handle events of event-type.

  (defn my-foo-handler [event] (do-stuff (:val event)))

  (on ::foo my-foo-handler)
  (event ::foo :val 200) ; my-foo-handler gets called with {:event-type ::foo :val 200}
  (remove-handler ::foo my-foo-handler)
  (event ::foo :val 200) ; my-foo-handler no longer called
  "
  [event-type handler]
  (dosync
    (let [handlers (get @event-handlers* event-type #{})]
      (alter event-handlers* assoc event-type (difference handlers #{handler})))))

(defn clear-handlers
  "Remove all handlers for events of type event-type."
  [event-type]
  (dosync (alter event-handlers* dissoc event-type))
  nil)

(defn- handle-event
  "Runs the event handlers for the given event, and removes any handler that returns :done."
  [event]
  (log/debug "handling event: " event)
  (let [event-type (:event-type event)
        handlers (get @event-handlers* event-type #{})
        keepers  (set (doall (filter #(not (= :done (run-handler % event))) handlers)))]
    (dosync (alter event-handlers* assoc event-type
                   (intersection keepers (get @event-handlers* event-type #{}))))))

(defn event
  "Fire an event of type event-type with any number of additional properties.
  NOTE: an event requires key/value pairs, and everything gets wrapped into an
  event map.  It will not work if you just pass values.

  (event ::my-event)
  (event ::filter-sweep-done :instrument :phat-bass)"
  [event-type & args]
  {:pre [(even? (count args))]}
  (log/debug "event: " event-type args)
  (.execute thread-pool #(handle-event (apply hash-map :event-type event-type args))))

(defn sync-event
  "Experimental synchronous event send."
  [event-type & args]
  {:pre [(even? (count args))]}
  (log/debug "synchronous event: " event-type args)
  (handle-event (apply hash-map :event-type event-type args)))

