(ns overtone.core.ugen.grain)

(def specs
     [
      ;; GrainSin : MultiOutUGen {
      ;;  *ar { arg numChannels = 1, trigger = 0, dur = 1, freq = 440, pan = 0, envbufnum = -1,
      ;;      mul = 1, add = 0;
      ;;    ^this.multiNew('audio', numChannels, trigger, dur, freq, pan, envbufnum).madd(mul, add);
      ;;    }
      ;;  init { arg argNumChannels ... theInputs;
      ;;    inputs = theInputs;
      ;;    ^this.initOutputs(argNumChannels, rate);
      ;;  }
      ;;  argNamesInputsOffset { ^2 }
      ;;  }

      {:name "GrainSin"
       :args [{:name "numChannels" :mode :num-outs :default 1}
              {:name "trigger" :default 0}
              {:name "dur" :default 1}
              {:name "freq" :default 440.0}
              {:name "pan" :default 0}
              {:name "envbufnum" :default -1}]
       :rates #{:ar}}

      ;; GrainFM : MultiOutUGen {
      ;;  *ar { arg numChannels = 1, trigger = 0, dur = 1, carfreq = 440, modfreq = 200, index = 1,
      ;;      pan = 0, envbufnum = -1, mul = 1, add = 0;
      ;;    ^this.multiNew('audio', numChannels, trigger, dur, carfreq, modfreq, index, pan, envbufnum)
      ;;      .madd(mul, add);
      ;;    }

      ;;  init { arg argNumChannels ... theInputs;
      ;;    inputs = theInputs;
      ;;    ^this.initOutputs(argNumChannels, rate);
      ;;  }

      ;;  argNamesInputsOffset { ^2 }
      ;;  }

      ;; GrainBuf : MultiOutUGen {
      ;;  *ar { arg numChannels = 1, trigger = 0, dur = 1, sndbuf, rate = 1, pos = 0, interp = 2,
      ;;      pan = 0, envbufnum = -1, mul = 1, add = 0;
      ;;    ^this.multiNew('audio', numChannels, trigger, dur, sndbuf, rate, pos, interp, pan,
      ;;      envbufnum).madd(mul, add);
      ;;    }

      ;;  init { arg argNumChannels ... theInputs;
      ;;    inputs = theInputs;
      ;;    ^this.initOutputs(argNumChannels, rate);
      ;;  }
      ;;  argNamesInputsOffset { ^2 }
      ;;  }

      ;; GrainIn : MultiOutUGen {
      ;;  *ar { arg numChannels = 1, trigger = 0, dur = 1, in, pan = 0, envbufnum = -1, mul = 1, add = 0;
      ;;    ^this.multiNew('audio', numChannels, trigger, dur, in, pan, envbufnum).madd(mul, add);
      ;;    }

      ;;  init { arg argNumChannels ... theInputs;
      ;;    inputs = theInputs;
      ;;    ^this.initOutputs(argNumChannels, rate);
      ;;  }
      ;;  argNamesInputsOffset { ^2 }
      ;;  }
      {:name "GrainIn"
       :args [{:name "numChannels" :mode :num-outs :default 1}
              {:name "trigger" :default 0}
              {:name "dur" :default 1}
              {:name "in" :default :none}
              {:name "pan" :default 0}
              {:name "envbufnum" :default -1}]
       :rates #{:ar}}

      ;; Warp1 : MultiOutUGen {
      ;;  *ar { arg numChannels = 1, bufnum=0, pointer=0, freqScale = 1,
      ;;      windowSize = 0.2, envbufnum = -1, overlaps = 8, windowRandRatio = 0.0, interp=1,
      ;;      mul = 1, add = 0;
      ;;    ^this.multiNew('audio', numChannels, bufnum, pointer, freqScale,
      ;;      windowSize, envbufnum, overlaps, windowRandRatio, interp).madd(mul, add);
      ;;  }
      ;;  init { arg argNumChannels ... theInputs;
      ;;    inputs = theInputs;
      ;;    ^this.initOutputs(argNumChannels, rate);
      ;;  }
      ;;  argNamesInputsOffset { ^2 }
      ;;  }

      {:name "Warp1"
       :args [{:name "numChannels" :mode :num-outs :default 1}
              {:name "bufnum" :default 0}
              {:name "pointer" :default 0}
              {:name "freqScale" :default 1}
              {:name "windowSize" :default 0.1}
              {:name "envbufnum" :default -1}
              {:name "overlaps" :default 8}
              {:name "windowRandRatio" :default 0.0}
              {:name "interp" :default 1}]
       :rates #{:ar}
       :muladd true}])
