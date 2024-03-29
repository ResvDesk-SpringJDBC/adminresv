/**
 * SoundManager 2 homepage demo JS
 * -------------------------------
 * MP3 player button, Muxtape/page player,
 * inline player and 360 player demo bits
 * combined and minified for optimal performance.
 * For raw source, see individual demo pages.
 * --------------------------------
 * Source files:
 * demo/play-mp3-links/script/inlineplayer.js
 * demo/page-player/script/page-player.js
 * demo/mp3-player-button/script/mp3-player-button.js
 * demo/360-player/script/berniecode-animator.js
 * demo/360-player/script/360player.js
 * demo/index.js
 */

/*

 Animator.js 1.1.9

 This library is released under the BSD license:

 Copyright (c) 2006, Bernard Sumption. All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer. Redistributions in binary
 form must reproduce the above copyright notice, this list of conditions and
 the following disclaimer in the documentation and/or other materials
 provided with the distribution. Neither the name BernieCode nor
 the names of its contributors may be used to endorse or promote products
 derived from this software without specific prior written permission. 

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 DAMAGE.

*/



function InlinePlayer() {
  var a = this,
    c = this,
    b = soundManager,
    d = navigator.userAgent.match(/msie/i);
  this.playableClass = "inline-playable";
  this.excludeClass = "inline-exclude";
  this.links = [];
  this.sounds = [];
  this.soundsByURL = [];
  this.indexByURL = [];
  this.lastSound = null;
  this.soundCount = 0;
  this.config = {
    playNext: !1,
    autoPlay: !1
  };
  this.css = {
    sDefault: "sm2_link",
    sLoading: "sm2_loading",
    sPlaying: "sm2_playing",
    sPaused: "sm2_paused"
  };
  this.addEventHandler = "undefined" !== typeof window.addEventListener ? function(a, b, c) {
    return a.addEventListener(b,
    c, !1)
  } : function(a, b, c) {
    a.attachEvent("on" + b, c)
  };
  this.removeEventHandler = "undefined" !== typeof window.removeEventListener ? function(a, b, c) {
    return a.removeEventListener(b, c, !1)
  } : function(a, b, c) {
    return a.detachEvent("on" + b, c)
  };
  this.classContains = function(a, b) {
    return "undefined" != typeof a.className ? a.className.match(RegExp("(\\s|^)" + b + "(\\s|$)")) : !1
  };
  this.addClass = function(b, c) {
    if (!b || !c || a.classContains(b, c)) return !1;
    b.className = (b.className ? b.className + " " : "") + c
  };
  this.removeClass = function(b, c) {
    if (!b || !c || !a.classContains(b, c)) return !1;
    b.className = b.className.replace(RegExp("( " + c + ")|(" + c + ")", "g"), "")
  };
  this.getSoundByURL = function(b) {
    return "undefined" != typeof a.soundsByURL[b] ? a.soundsByURL[b] : null
  };
  this.isChildOfNode = function(a, b) {
    if (!a || !a.parentNode) return !1;
    b = b.toLowerCase();
    do a = a.parentNode;
    while (a && a.parentNode && a.nodeName.toLowerCase() != b);
    return a.nodeName.toLowerCase() == b ? a : null
  };
  this.events = {
    play: function() {
      c.removeClass(this._data.oLink, this._data.className);
      this._data.className = c.css.sPlaying;
      c.addClass(this._data.oLink, this._data.className)
    },
    stop: function() {
      c.removeClass(this._data.oLink, this._data.className);
      this._data.className = ""
    },
    pause: function() {
      c.removeClass(this._data.oLink, this._data.className);
      this._data.className = c.css.sPaused;
      c.addClass(this._data.oLink, this._data.className)
    },
    resume: function() {
      c.removeClass(this._data.oLink, this._data.className);
      this._data.className = c.css.sPlaying;
      c.addClass(this._data.oLink, this._data.className)
    },
    finish: function() {
      c.removeClass(this._data.oLink,
      this._data.className);
      this._data.className = "";
      if (c.config.playNext) {
        var a = c.indexByURL[this._data.oLink.href] + 1;
        a < c.links.length && c.handleClick({
          target: c.links[a]
        })
      }
    }
  };
  this.stopEvent = function(a) {
    "undefined" != typeof a && "undefined" != typeof a.preventDefault ? a.preventDefault() : "undefined" != typeof event && "undefined" != typeof event.returnValue && (event.returnValue = !1);
    return !1
  };
  this.getTheDamnLink = d ? function(a) {
    return a && a.target ? a.target : window.event.srcElement
  } : function(a) {
    return a.target
  };
  this.handleClick = function(c) {
    if ("undefined" != typeof c.button && 1 < c.button) return !0;
    var d = a.getTheDamnLink(c);
    if ("a" != d.nodeName.toLowerCase() && (d = a.isChildOfNode(d, "a"), !d)) return !0;
    d.getAttribute("href");
    if (!d.href || !b.canPlayLink(d) && !a.classContains(d, a.playableClass) || a.classContains(d, a.excludeClass)) return !0;
    var h = d.href,
      g = a.getSoundByURL(h);
    g ? (g != a.lastSound && (b._writeDebug("sound different than last sound: " + a.lastSound.id), a.lastSound && a.stopSound(a.lastSound)), g.togglePause()) : (a.lastSound && a.stopSound(a.lastSound),
    g = b.createSound({
      id: "inlineMP3Sound" + a.soundCount++,
      url: h,
      onplay: a.events.play,
      onstop: a.events.stop,
      onpause: a.events.pause,
      onresume: a.events.resume,
      onfinish: a.events.finish,
      type: d.type || null
    }), g._data = {
      oLink: d,
      className: a.css.sPlaying
    }, a.soundsByURL[h] = g, a.sounds.push(g), g.play());
    a.lastSound = g;
    "undefined" != typeof c && "undefined" != typeof c.preventDefault ? c.preventDefault() : event.returnValue = !1;
    return !1
  };
  this.stopSound = function(a) {
    soundManager.stop(a.id);
    soundManager.unload(a.id)
  };
  this.init = function() {
    b._writeDebug("inlinePlayer.init()");
    for (var c = document.getElementsByTagName("a"), d = 0, h = 0, g = c.length; h < g; h++) if ((b.canPlayLink(c[h]) || a.classContains(c[h], a.playableClass)) && !a.classContains(c[h], a.excludeClass)) a.addClass(c[h], a.css.sDefault), a.links[d] = c[h], a.indexByURL[c[h].href] = d, d++;
    0 < d && (a.addEventHandler(document, "click", a.handleClick), a.config.autoPlay && a.handleClick({
      target: a.links[0],
      preventDefault: function() {}
    }));
    b._writeDebug("inlinePlayer.init(): Found " + d + " relevant items.")
  };
  this.init()
}
var inlinePlayer = null;
soundManager.setup({
  debugMode: !0,
  preferFlash: !1,
  useFlashBlock: !0,
  url: "../../swf/",
  flashVersion: 9
});
soundManager.onready(function() {
  inlinePlayer = new InlinePlayer
});
var pagePlayer = null;

function PagePlayer() {
  var a = this,
    c = this,
    b = soundManager,
    d, f = null,
    e = null,
    h = document.getElementsByTagName("head")[0],
    g = null,
    k = navigator.userAgent,
    m = k.match(/(opera|firefox)/i),
    p = k.match(/ipad|ipod|iphone/i),
    q;
  this.config = {
    usePeakData: !1,
    useWaveformData: !1,
    useEQData: !1,
    fillGraph: !1,
    allowRightClick: !0,
    useThrottling: !0,
    autoStart: !1,
    playNext: !0,
    updatePageTitle: !0,
    emptyTime: "-:--",
    useFavIcon: !1
  };
  this.css = {
    sDefault: "sm2_link",
    sLoading: "sm2_loading",
    sPlaying: "sm2_playing",
    sPaused: "sm2_paused"
  };
  this.sounds = [];
  this.soundsByObject = [];
  this.lastSound = null;
  this.soundCount = 0;
  this.strings = [];
  this.dragActive = !1;
  this.dragExec = new Date;
  this.dragTimer = null;
  this.pageTitle = document.title;
  this.lastWPExec = new Date;
  this.lastWLExec = new Date;
  this.vuMeterData = [];
  this.oControls = null;
  this._mergeObjects = function(a, b) {
    var c = {}, d, e;
    for (d in a) a.hasOwnProperty(d) && (c[d] = a[d]);
    d = "undefined" === typeof b ? {} : b;
    for (e in d) "undefined" === typeof c[e] && (c[e] = d[e]);
    return c
  };
  d = function() {
    function a(b) {
      b = d.call(b);
      var l = b.length;
      c ? (b[1] =
        "on" + b[1], 3 < l && b.pop()) : 3 === l && b.push(!1);
      return b
    }
    function b(a, l) {
      var n = a.shift(),
        d = [e[l]];
      if (c) n[d](a[0], a[1]);
      else n[d].apply(n, a)
    }
    var c = window.attachEvent && !window.addEventListener,
      d = Array.prototype.slice,
      e = {
        add: c ? "attachEvent" : "addEventListener",
        remove: c ? "detachEvent" : "removeEventListener"
      };
    return {
      add: function() {
        b(a(arguments), "add")
      },
      remove: function() {
        b(a(arguments), "remove")
      }
    }
  }();
  this.hasClass = function(a, b) {
    return "undefined" !== typeof a.className ? RegExp("(^|\\s)" + b + "(\\s|$)").test(a.className) : !1
  };
  this.addClass = function(b, l) {
    if (!b || !l || a.hasClass(b, l)) return !1;
    b.className = (b.className ? b.className + " " : "") + l
  };
  this.removeClass = function(b, l) {
    if (!b || !l || !a.hasClass(b, l)) return !1;
    b.className = b.className.replace(RegExp("( " + l + ")|(" + l + ")", "g"), "")
  };
  this.select = function(b, l) {
    var c = a.getByClassName(b, "div", l || null);
    return c ? c[0] : null
  };
  this.getByClassName = document.querySelectorAll ? function(a, b, c) {
    a = "." + a;
    b && (b = b.split(" "));
    b = 1 < b.length ? b.join(a + ", ") : b[0] + a;
    return (c ? c : document).querySelectorAll(b)
  } : function(b, l, c) {
    var d = c ? c : document;
    c = [];
    var e, g = [];
    l && (l = l.split(" "));
    if (l instanceof Array) {
      for (e = l.length; e--;) if (!g || !g[l[e]]) g[l[e]] = d.getElementsByTagName(l[e]);
      for (e = l.length; e--;) for (d = g[l[e]].length; d--;) a.hasClass(g[l[e]][d], b) && c.push(g[l[e]][d])
    } else {
      g = d.all || d.getElementsByTagName("*");
      e = 0;
      for (d = g.length; e < d; e++) a.hasClass(g[e], b) && c.push(g[e])
    }
    return c
  };
  this.isChildOfClass = function(b, l) {
    if (!b || !l) return !1;
    for (; b.parentNode && !a.hasClass(b, l);) b = b.parentNode;
    return a.hasClass(b, l)
  };
  this.getParentByNodeName = function(a, b) {
    if (!a || !b) return !1;
    for (b = b.toLowerCase(); a.parentNode && b !== a.parentNode.nodeName.toLowerCase();) a = a.parentNode;
    return a.parentNode && b === a.parentNode.nodeName.toLowerCase() ? a.parentNode : null
  };
  this.getOffX = function(a) {
    var b = 0;
    if (a.offsetParent) for (; a.offsetParent;) b += a.offsetLeft, a = a.offsetParent;
    else a.x && (b += a.x);
    return b
  };
  this.getTime = function(a, b) {
    var c = Math.floor(a / 1E3),
      d = Math.floor(c / 60),
      c = c - 60 * d;
    return b ? d + ":" + (10 > c ? "0" + c : c) : {
      min: d,
      sec: c
    }
  };
  this.getSoundByObject = function(b) {
    return "undefined" !== typeof a.soundsByObject[b.id] ? a.soundsByObject[b.id] : null
  };
  this.getPreviousItem = function(a) {
    if (a.previousElementSibling) a = a.previousElementSibling;
    else for (a = a.previousSibling; a && a.previousSibling && 1 !== a.previousSibling.nodeType;) a = a.previousSibling;
    return "li" !== a.nodeName.toLowerCase() ? null : a.getElementsByTagName("a")[0]
  };
  this.playPrevious = function(b) {
    b || (b = a.lastSound);
    if (!b) return !1;
    (b = a.getPreviousItem(b._data.oLI)) && c.handleClick({
      target: b
    });
    return b
  };
  this.getNextItem = function(a) {
    if (a.nextElementSibling) a = a.nextElementSibling;
    else for (a = a.nextSibling; a && a.nextSibling && 1 !== a.nextSibling.nodeType;) a = a.nextSibling;
    return "li" !== a.nodeName.toLowerCase() ? null : a.getElementsByTagName("a")[0]
  };
  this.playNext = function(b) {
    b || (b = a.lastSound);
    if (!b) return !1;
    (b = a.getNextItem(b._data.oLI)) && c.handleClick({
      target: b
    });
    return b
  };
  this.setPageTitle = function(b) {
    if (!a.config.updatePageTitle) return !1;
    try {
      document.title = (b ? b + " - " : "") + a.pageTitle
    } catch (c) {
      a.setPageTitle = function() {
        return !1
      }
    }
  };
  this.events = {
    play: function() {
      c.removeClass(this._data.oLI,
      this._data.className);
      this._data.className = c.css.sPlaying;
      c.addClass(this._data.oLI, this._data.className);
      a.setPageTitle(this._data.originalTitle)
    },
    stop: function() {
      c.removeClass(this._data.oLI, this._data.className);
      this._data.className = "";
      this._data.oPosition.style.width = "0px";
      a.setPageTitle();
      a.resetPageIcon()
    },
    pause: function() {
      if (c.dragActive) return !1;
      c.removeClass(this._data.oLI, this._data.className);
      this._data.className = c.css.sPaused;
      c.addClass(this._data.oLI, this._data.className);
      a.setPageTitle();
      a.resetPageIcon()
    },
    resume: function() {
      if (c.dragActive) return !1;
      c.removeClass(this._data.oLI, this._data.className);
      this._data.className = c.css.sPlaying;
      c.addClass(this._data.oLI, this._data.className)
    },
    finish: function() {
      c.removeClass(this._data.oLI, this._data.className);
      this._data.className = "";
      this._data.oPosition.style.width = "0px";
      a.config.playNext ? c.playNext(this) : (a.setPageTitle(), a.resetPageIcon())
    },
    whileloading: function() {
      function b() {
        this._data.oLoading.style.width = 100 * (this.bytesLoaded / this.bytesTotal) +
          "%";
        !this._data.didRefresh && this._data.metadata && (this._data.didRefresh = !0, this._data.metadata.refresh())
      }
      if (c.config.useThrottling) {
        var l = new Date;
        if (l && 50 < l - a.lastWLExec || this.bytesLoaded === this.bytesTotal) b.apply(this), a.lastWLExec = l
      } else b.apply(this)
    },
    onload: function() {
      if (this.loaded) this._data.metadata && this._data.metadata.refresh();
      else {
        var a = this._data.oLI.getElementsByTagName("a")[0],
          c = a.innerHTML;
        a.innerHTML = c + ' <span style="font-size:0.5em"> | Load failed, d\'oh! ' + (b.sandbox.noRemote ?
          " Possible cause: Flash sandbox is denying remote URL access." : b.sandbox.noLocal ? "Flash denying local filesystem access" : "404?") + "</span>";
        setTimeout(function() {
          a.innerHTML = c
        }, 5E3)
      }
    },
    whileplaying: function() {
      var d = null;
      if (c.dragActive || !c.config.useThrottling) {
        a.updateTime.apply(this);
        9 <= b.flashVersion && (c.config.usePeakData && this.instanceOptions.usePeakData && a.updatePeaks.apply(this), (c.config.useWaveformData && this.instanceOptions.useWaveformData || c.config.useEQData && this.instanceOptions.useEQData) && a.updateGraph.apply(this));
        if (this._data.metadata && (d = new Date) && 500 < d - a.lastWPExec) this._data.metadata.refreshMetadata(this), a.lastWPExec = d;
        this._data.oPosition.style.width = 100 * (this.position / a.getDurationEstimate(this)) + "%"
      } else d = new Date, 30 < d - a.lastWPExec && (a.updateTime.apply(this), 9 <= b.flashVersion && (c.config.usePeakData && this.instanceOptions.usePeakData && a.updatePeaks.apply(this), (c.config.useWaveformData && this.instanceOptions.useWaveformData || c.config.useEQData && this.instanceOptions.useEQData) && a.updateGraph.apply(this)), this._data.metadata && this._data.metadata.refreshMetadata(this), this._data.oPosition.style.width = 100 * (this.position / a.getDurationEstimate(this)) + "%", a.lastWPExec = d)
    }
  };
  this.setPageIcon = function(b) {
    if (!a.config.useFavIcon || !a.config.usePeakData || !b) return !1;
    var c = document.getElementById("sm2-favicon");
    c && (h.removeChild(c), c = null);
    c || (c = document.createElement("link"), c.id = "sm2-favicon", c.rel = "shortcut icon", c.type = "image/png", c.href = b, document.getElementsByTagName("head")[0].appendChild(c))
  };
  this.resetPageIcon = function() {
    if (!a.config.useFavIcon) return !1;
    var b = document.getElementById("favicon");
    b && (b.href = "/favicon.ico")
  };
  this.updatePeaks = function() {
    var c = this._data.oPeak.getElementsByTagName("span");
    c[0].style.marginTop = 13 - Math.floor(15 * this.peakData.left) + "px";
    c[1].style.marginTop = 13 - Math.floor(15 * this.peakData.right) + "px";
    8 < b.flashVersion && (a.config.useFavIcon && a.config.usePeakData) && a.setPageIcon(a.vuMeterData[parseInt(16 * this.peakData.left, 10)][parseInt(16 * this.peakData.right, 10)])
  };
  this.updateGraph = function() {
    if (9 > c.config.flashVersion || !c.config.useWaveformData && !c.config.useEQData) return !1;
    var a = this._data.oGraph.getElementsByTagName("div"),
      b;
    if (c.config.useWaveformData) for (b = 255; b--;) a[255 - b].style.marginTop = 9 + Math.ceil(-8 * this.waveformData.left[b]) + "px";
    else for (b = 255; b--;) a[255 - b].style.marginTop = 17 + Math.ceil(-9 * this.eqData[b]) + "px"
  };
  this.resetGraph = function() {
    if (!c.config.useEQData || 9 > c.config.flashVersion) return !1;
    var a = this._data.oGraph.getElementsByTagName("div"),
      b = !c.config.useEQData ? "9px" : "17px",
      d = !c.config.fillGraph ? "1px" : "32px",
      e;
    for (e = 255; e--;) a[255 - e].style.marginTop = b, a[255 - e].style.height = d
  };
  this.updateTime = function() {
    var b = a.strings.timing.replace("%s1", a.getTime(this.position, !0)),
      b = b.replace("%s2", a.getTime(a.getDurationEstimate(this), !0));
    this._data.oTiming.innerHTML = b
  };
  this.getTheDamnTarget = function(a) {
    return a.target || (window.event ? window.event.srcElement : null)
  };
  this.withinStatusBar = function(b) {
    return a.isChildOfClass(b, "playlist") && a.isChildOfClass(b,
      "controls")
  };
  this.handleClick = function(d) {
    if (2 === d.button) return c.config.allowRightClick || c.stopEvent(d), c.config.allowRightClick;
    var l = a.getTheDamnTarget(d),
      r, e, f;
    if (!l) return !0;
    a.dragActive && a.stopDrag();
    if (a.withinStatusBar(l)) return !1;
    "a" !== l.nodeName.toLowerCase() && (l = a.getParentByNodeName(l, "a"));
    if (!l) return !0;
    l.getAttribute("href");
    if (!l.href || !b.canPlayLink(l) && !a.hasClass(l, "playable") || a.hasClass(l, "exclude")) return !0;
    a.initUL(a.getParentByNodeName(l, "ul"));
    a.initItem(l);
    r = l.href;
    (e = a.getSoundByObject(l)) ? (a.setPageTitle(e._data.originalTitle), e === a.lastSound ? 2 !== e.readyState ? 1 !== e.playState ? e.play() : e.togglePause() : b._writeDebug("Warning: sound failed to load (security restrictions, 404 or bad format)", 2) : (a.lastSound && a.stopSound(a.lastSound), g && e._data.oTimingBox.appendChild(g), e.togglePause())) : (e = b.createSound({
      id: l.id,
      url: decodeURI(r),
      onplay: a.events.play,
      onstop: a.events.stop,
      onpause: a.events.pause,
      onresume: a.events.resume,
      onfinish: a.events.finish,
      type: l.type || null,
      whileloading: a.events.whileloading,
      whileplaying: a.events.whileplaying,
      onmetadata: a.events.metadata,
      onload: a.events.onload
    }), r = a.oControls.cloneNode(!0), f = l.parentNode, f.appendChild(r), g && f.appendChild(g), a.soundsByObject[l.id] = e, e._data = {
      oLink: l,
      oLI: f,
      oControls: a.select("controls", f),
      oStatus: a.select("statusbar", f),
      oLoading: a.select("loading", f),
      oPosition: a.select("position", f),
      oTimingBox: a.select("timing", f),
      oTiming: a.select("timing", f).getElementsByTagName("div")[0],
      oPeak: a.select("peak", f),
      oGraph: a.select("spectrum-box", f),
      className: a.css.sPlaying,
      originalTitle: l.innerHTML,
      metadata: null
    }, g && e._data.oTimingBox.appendChild(g), e._data.oLI.getElementsByTagName("ul").length && (e._data.metadata = new Metadata(e)), l = a.strings.timing.replace("%s1", a.config.emptyTime), l = l.replace("%s2", a.config.emptyTime), e._data.oTiming.innerHTML = l, a.sounds.push(e), a.lastSound && a.stopSound(a.lastSound), a.resetGraph.apply(e), e.play());
    a.lastSound = e;
    return a.stopEvent(d)
  };
  this.handleMouseDown = function(b) {
    p && b.touches && (b = b.touches[0]);
    if (2 === b.button) return c.config.allowRightClick || c.stopEvent(b), c.config.allowRightClick;
    var l = a.getTheDamnTarget(b);
    if (!l || !a.withinStatusBar(l)) return !0;
    a.dragActive = !0;
    a.lastSound.pause();
    a.setPosition(b);
    p ? d.add(document, "touchmove", a.handleMouseMove) : d.add(document, "mousemove", a.handleMouseMove);
    a.addClass(a.lastSound._data.oControls, "dragging");
    return a.stopEvent(b)
  };
  this.handleMouseMove = function(b) {
    p && b.touches && (b = b.touches[0]);
    if (a.dragActive) if (a.config.useThrottling) {
      var c = new Date;
      20 < c - a.dragExec ? a.setPosition(b) : (window.clearTimeout(a.dragTimer),
      a.dragTimer = window.setTimeout(function() {
        a.setPosition(b)
      }, 20));
      a.dragExec = c
    } else a.setPosition(b);
    else a.stopDrag();
    b.stopPropagation = !0;
    return !1
  };
  this.stopDrag = function(b) {
    if (a.dragActive) return a.removeClass(a.lastSound._data.oControls, "dragging"), p ? d.remove(document, "touchmove", a.handleMouseMove) : d.remove(document, "mousemove", a.handleMouseMove), c.hasClass(a.lastSound._data.oLI, a.css.sPaused) || a.lastSound.resume(), a.dragActive = !1, a.stopEvent(b)
  };
  this.handleStatusClick = function(b) {
    a.setPosition(b);
    c.hasClass(a.lastSound._data.oLI, a.css.sPaused) || a.resume();
    return a.stopEvent(b)
  };
  this.stopEvent = function(a) {
    "undefined" !== typeof a && ("undefined" !== typeof a.preventDefault ? a.preventDefault() : (a.stopPropagation = !0, a.returnValue = !1));
    return !1
  };
  this.setPosition = function(b) {
    var c = a.getTheDamnTarget(b),
      d;
    if (!c) return !0;
    for (d = c; !a.hasClass(d, "controls") && d.parentNode;) d = d.parentNode;
    c = a.lastSound;
    b = parseInt(b.clientX, 10);
    b = Math.floor((b - a.getOffX(d) - 4) / d.offsetWidth * a.getDurationEstimate(c));
    isNaN(b) || (b = Math.min(b, c.duration));
    isNaN(b) || c.setPosition(b)
  };
  this.stopSound = function(a) {
    b._writeDebug("stopping sound: " + a.id);
    b.stop(a.id);
    p || b.unload(a.id)
  };
  this.getDurationEstimate = function(a) {
    return a.instanceOptions.isMovieStar ? a.duration : !a._data.metadata || !a._data.metadata.data.givenDuration ? a.durationEstimate || 0 : a._data.metadata.data.givenDuration
  };
  this.createVUData = function() {
    var b = 0,
      c = 0,
      d = f.getContext("2d"),
      e = d.createLinearGradient(0, 16, 0, 0),
      g;
    e.addColorStop(0, "rgb(0,192,0)");
    e.addColorStop(0.3,
      "rgb(0,255,0)");
    e.addColorStop(0.625, "rgb(255,255,0)");
    e.addColorStop(0.85, "rgb(255,0,0)");
    g = d.createLinearGradient(0, 16, 0, 0);
    g.addColorStop(0, "rgba(0,0,0,0.2)");
    g.addColorStop(1, "rgba(0,0,0,0.5)");
    for (b = 0; 16 > b; b++) a.vuMeterData[b] = [];
    for (b = 0; 16 > b; b++) for (c = 0; 16 > c; c++) f.setAttribute("width", 16), f.setAttribute("height", 16), d.fillStyle = g, d.fillRect(0, 0, 7, 15), d.fillRect(8, 0, 7, 15), d.fillStyle = e, d.fillRect(0, 15 - b, 7, 16 - (16 - b)), d.fillRect(8, 15 - c, 7, 16 - (16 - c)), d.clearRect(0, 3, 16, 1), d.clearRect(0, 7, 16, 1), d.clearRect(0,
    11, 16, 1), a.vuMeterData[b][c] = f.toDataURL("image/png")
  };
  this.testCanvas = function() {
    var a = document.createElement("canvas"),
      b = null;
    if (!a || "undefined" === typeof a.getContext) return null;
    b = a.getContext("2d");
    if (!b || "function" !== typeof a.toDataURL) return null;
    try {
      a.toDataURL("image/png")
    } catch (c) {
      return null
    }
    return a
  };
  this.initItem = function(b) {
    b.id || (b.id = "pagePlayerMP3Sound" + a.soundCount++);
    a.addClass(b, a.css.sDefault)
  };
  this.initUL = function(c) {
    9 <= b.flashVersion && a.addClass(c, a.cssBase)
  };
  this.init = function(h) {
    function l(b) {
      d[b](document,
        "click", a.handleClick);
      p ? (d[b](document, "touchstart", a.handleMouseDown), d[b](document, "touchend", a.stopDrag)) : (d[b](document, "mousedown", a.handleMouseDown), d[b](document, "mouseup", a.stopDrag));
      d[b](window, "unload", q)
    }
    h ? (b._writeDebug("pagePlayer.init(): Using custom configuration"), this.config = this._mergeObjects(h, this.config)) : b._writeDebug("pagePlayer.init(): Using default configuration");
    var r, v, k, u;
    this.cssBase = [];
    b.useFlashBlock = !0;
    9 <= b.flashVersion ? (b.defaultOptions.usePeakData = this.config.usePeakData,
    b.defaultOptions.useWaveformData = this.config.useWaveformData, b.defaultOptions.useEQData = this.config.useEQData, this.config.usePeakData && this.cssBase.push("use-peak"), (this.config.useWaveformData || this.config.useEQData) && this.cssBase.push("use-spectrum"), this.cssBase = this.cssBase.join(" "), this.config.useFavIcon && ((f = a.testCanvas()) && m ? a.createVUData() : this.config.useFavIcon = !1)) : (this.config.usePeakData || this.config.useWaveformData || this.config.useEQData) && b._writeDebug("Page player: Note: soundManager.flashVersion = 9 is required for peak/waveform/EQ features.");
    e = document.createElement("div");
    e.innerHTML = '  <div class="controls">\n   <div class="statusbar">\n    <div class="loading"></div>\n    <div class="position"></div>\n   </div>\n  </div>\n  <div class="timing">\n   <div id="sm2_timing" class="timing-data">\n    <span class="sm2_position">%s1</span> / <span class="sm2_total">%s2</span>\n   </div>\n  </div>\n  <div class="peak">\n   <div class="peak-box"><span class="l"></span><span class="r"></span></div>\n  </div>\n <div class="spectrum-container">\n  <div class="spectrum-box">\n   <div class="spectrum"></div>\n  </div>\n </div>';
    if (9 <= b.flashVersion) {
      g = a.select("spectrum-container", e);
      g = e.removeChild(g);
      r = a.select("spectrum-box", g);
      v = r.getElementsByTagName("div")[0];
      k = document.createDocumentFragment();
      u = null;
      for (h = 256; h--;) u = v.cloneNode(!1), u.style.left = h + "px", k.appendChild(u);
      r.removeChild(v);
      r.appendChild(k)
    } else e.removeChild(a.select("spectrum-container", e)), e.removeChild(a.select("peak", e));
    a.oControls = e.cloneNode(!0);
    h = a.select("timing-data", e);
    a.strings.timing = h.innerHTML;
    h.innerHTML = "";
    h.id = "";
    q = function() {
      l("remove")
    };
    l("add");
    b._writeDebug("pagePlayer.init(): Ready", 1);
    a.config.autoStart && c.handleClick({
      target: c.getByClassName("playlist", "ul")[0].getElementsByTagName("a")[0]
    })
  }
}
soundManager.useFlashBlock = !0;
soundManager.onready(function() {
  pagePlayer = new PagePlayer;
  pagePlayer.init("undefined" !== typeof PP_CONFIG ? PP_CONFIG : null)
});

function BasicMP3Player() {
  var a = this,
    c = this,
    b = soundManager,
    d = navigator.userAgent.match(/ipad|iphone/i),
    f = navigator.userAgent.match(/msie/i);
  this.excludeClass = "button-exclude";
  this.links = [];
  this.sounds = [];
  this.soundsByURL = {};
  this.indexByURL = {};
  this.lastSound = null;
  this.soundCount = 0;
  this.config = {
    playNext: !1,
    autoPlay: !1
  };
  this.css = {
    sDefault: "sm2_button",
    sLoading: "sm2_loading",
    sPlaying: "sm2_playing",
    sPaused: "sm2_paused"
  };
  this.includeClass = this.css.sDefault;
  this.addEventHandler = "undefined" !== typeof window.addEventListener ? function(a, b, c) {
    return a.addEventListener(b, c, !1)
  } : function(a, b, c) {
    a.attachEvent("on" + b, c)
  };
  this.removeEventHandler = "undefined" !== typeof window.removeEventListener ? function(a, b, c) {
    return a.removeEventListener(b, c, !1)
  } : function(a, b, c) {
    return a.detachEvent("on" + b, c)
  };
  this.classContains = function(a, b) {
    return "undefined" !== typeof a.className ? a.className.match(RegExp("(\\s|^)" + b + "(\\s|$)")) : !1
  };
  this.addClass = function(b, c) {
    if (!b || !c || a.classContains(b, c)) return !1;
    b.className = (b.className ? b.className + " " :
      "") + c
  };
  this.removeClass = function(b, c) {
    if (!b || !c || !a.classContains(b, c)) return !1;
    b.className = b.className.replace(RegExp("( " + c + ")|(" + c + ")", "g"), "")
  };
  this.getSoundByURL = function(b) {
    return "undefined" !== typeof a.soundsByURL[b] ? a.soundsByURL[b] : null
  };
  this.isChildOfNode = function(b, a) {
    if (!b || !b.parentNode) return !1;
    a = a.toLowerCase();
    do b = b.parentNode;
    while (b && b.parentNode && b.nodeName.toLowerCase() !== a);
    return b.nodeName.toLowerCase() === a ? b : null
  };
  this.events = {
    play: function() {
      c.removeClass(this._data.oLink,
      this._data.className);
      this._data.className = c.css.sPlaying;
      c.addClass(this._data.oLink, this._data.className)
    },
    stop: function() {
      c.removeClass(this._data.oLink, this._data.className);
      this._data.className = ""
    },
    pause: function() {
      c.removeClass(this._data.oLink, this._data.className);
      this._data.className = c.css.sPaused;
      c.addClass(this._data.oLink, this._data.className)
    },
    resume: function() {
      c.removeClass(this._data.oLink, this._data.className);
      this._data.className = c.css.sPlaying;
      c.addClass(this._data.oLink, this._data.className)
    },
    finish: function() {
      c.removeClass(this._data.oLink, this._data.className);
      this._data.className = "";
      if (c.config.playNext) {
        var b = c.indexByURL[this._data.oLink.href] + 1;
        b < c.links.length && c.handleClick({
          target: c.links[b]
        })
      }
    }
  };
  this.stopEvent = function(b) {
    "undefined" !== typeof b && "undefined" !== typeof b.preventDefault ? b.preventDefault() : "undefined" !== typeof window.event && (window.event.returnValue = !1);
    return !1
  };
  this.getTheDamnLink = f ? function(b) {
    return b && b.target ? b.target : window.event.srcElement
  } : function(b) {
    return b.target
  };
  this.handleClick = function(c) {
    if ("undefined" !== typeof c.button && 1 < c.button) return !0;
    var d = a.getTheDamnLink(c),
      f, k;
    if ("a" !== d.nodeName.toLowerCase() && (d = a.isChildOfNode(d, "a"), !d)) return !0;
    d.getAttribute("href");
    if (!d.href || (!soundManager.canPlayLink(d) || a.classContains(d, a.excludeClass)) || !a.classContains(d, a.includeClass)) return !0;
    b._writeDebug("handleClick()");
    f = d.href;
    (k = a.getSoundByURL(f)) ? k === a.lastSound ? k.togglePause() : (k.togglePause(), b._writeDebug("sound different than last sound: " + a.lastSound.id),
    a.lastSound && a.stopSound(a.lastSound)) : (k = b.createSound({
      id: "basicMP3Sound" + a.soundCount++,
      url: f,
      onplay: a.events.play,
      onstop: a.events.stop,
      onpause: a.events.pause,
      onresume: a.events.resume,
      onfinish: a.events.finish,
      type: d.type || null
    }), k._data = {
      oLink: d,
      className: a.css.sPlaying
    }, a.soundsByURL[f] = k, a.sounds.push(k), a.lastSound && a.stopSound(a.lastSound), k.play());
    a.lastSound = k;
    return a.stopEvent(c)
  };
  this.stopSound = function(b) {
    soundManager.stop(b.id);
    d || soundManager.unload(b.id)
  };
  this.init = function() {
    b._writeDebug("basicMP3Player.init()");
    var c, d, f = 0,
      k = document.getElementsByTagName("a");
    c = 0;
    for (d = k.length; c < d; c++) a.classContains(k[c], a.css.sDefault) && !a.classContains(k[c], a.excludeClass) && (a.links[f] = k[c], a.indexByURL[k[c].href] = f, f++);
    0 < f && (a.addEventHandler(document, "click", a.handleClick), a.config.autoPlay && a.handleClick({
      target: a.links[0],
      preventDefault: function() {}
    }));
    b._writeDebug("basicMP3Player.init(): Found " + f + " relevant items.")
  };
  this.init()
}
var basicMP3Player = null;
soundManager.setup({
  preferFlash: !1,
  onready: function() {
    basicMP3Player = new BasicMP3Player
  }
});

function Animator(a) {
  this.setOptions(a);
  var c = this;
  this.timerDelegate = function() {
    c.onTimerEvent()
  };
  this.subjects = [];
  this.subjectScopes = [];
  this.state = this.target = 0;
  this.lastTime = null
}
Animator.prototype = {
  setOptions: function(a) {
    this.options = Animator.applyDefaults({
      interval: 20,
      duration: 400,
      onComplete: function() {},
      onStep: function() {},
      transition: Animator.tx.easeInOut
    }, a)
  },
  seekTo: function(a) {
    this.seekFromTo(this.state, a)
  },
  seekFromTo: function(a, c) {
    this.target = Math.max(0, Math.min(1, c));
    this.state = Math.max(0, Math.min(1, a));
    this.lastTime = (new Date).getTime();
    this.intervalId || (this.intervalId = window.setInterval(this.timerDelegate, this.options.interval))
  },
  jumpTo: function(a) {
    this.target = this.state = Math.max(0, Math.min(1, a));
    this.propagate()
  },
  toggle: function() {
    this.seekTo(1 - this.target)
  },
  addSubject: function(a, c) {
    this.subjects[this.subjects.length] = a;
    this.subjectScopes[this.subjectScopes.length] = c;
    return this
  },
  clearSubjects: function() {
    this.subjects = [];
    this.subjectScopes = []
  },
  propagate: function() {
    for (var a = this.options.transition(this.state), c = 0; c < this.subjects.length; c++) this.subjects[c].setState ? this.subjects[c].setState(a) : this.subjects[c].apply(this.subjectScopes[c], [a])
  },
  onTimerEvent: function() {
    var a = (new Date).getTime(),
      c = a - this.lastTime;
    this.lastTime = a;
    a = c / this.options.duration * (this.state < this.target ? 1 : -1);
    Math.abs(a) >= Math.abs(this.state - this.target) ? this.state = this.target : this.state += a;
    try {
      this.propagate()
    } finally {
      this.options.onStep.call(this), this.target == this.state && (window.clearInterval(this.intervalId), this.intervalId = null, this.options.onComplete.call(this))
    }
  },
  play: function() {
    this.seekFromTo(0, 1)
  },
  reverse: function() {
    this.seekFromTo(1, 0)
  },
  inspect: function() {
    for (var a = "#<Animator:\n",
    c = 0; c < this.subjects.length; c++) a += this.subjects[c].inspect();
    return a + ">"
  }
};
Animator.applyDefaults = function(a, c) {
  c = c || {};
  var b, d = {};
  for (b in a) d[b] = void 0 !== c[b] ? c[b] : a[b];
  return d
};
Animator.makeArray = function(a) {
  if (null == a) return [];
  if (!a.length) return [a];
  for (var c = [], b = 0; b < a.length; b++) c[b] = a[b];
  return c
};
Animator.camelize = function(a) {
  var c = a.split("-");
  if (1 == c.length) return c[0];
  a = 0 == a.indexOf("-") ? c[0].charAt(0).toUpperCase() + c[0].substring(1) : c[0];
  for (var b = 1, d = c.length; b < d; b++) {
    var f = c[b];
    a += f.charAt(0).toUpperCase() + f.substring(1)
  }
  return a
};
Animator.apply = function(a, c, b) {
  return c instanceof Array ? (new Animator(b)).addSubject(new CSSStyleSubject(a, c[0], c[1])) : (new Animator(b)).addSubject(new CSSStyleSubject(a, c))
};
Animator.makeEaseIn = function(a) {
  return function(c) {
    return Math.pow(c, 2 * a)
  }
};
Animator.makeEaseOut = function(a) {
  return function(c) {
    return 1 - Math.pow(1 - c, 2 * a)
  }
};
Animator.makeElastic = function(a) {
  return function(c) {
    c = Animator.tx.easeInOut(c);
    return (1 - Math.cos(c * Math.PI * a)) * (1 - c) + c
  }
};
Animator.makeADSR = function(a, c, b, d) {
  null == d && (d = 0.5);
  return function(f) {
    return f < a ? f / a : f < c ? 1 - (f - a) / (c - a) * (1 - d) : f < b ? d : d * (1 - (f - b) / (1 - b))
  }
};
Animator.makeBounce = function(a) {
  var c = Animator.makeElastic(a);
  return function(b) {
    b = c(b);
    return 1 >= b ? b : 2 - b
  }
};
Animator.tx = {
  easeInOut: function(a) {
    return -Math.cos(a * Math.PI) / 2 + 0.5
  },
  linear: function(a) {
    return a
  },
  easeIn: Animator.makeEaseIn(1.5),
  easeOut: Animator.makeEaseOut(1.5),
  strongEaseIn: Animator.makeEaseIn(2.5),
  strongEaseOut: Animator.makeEaseOut(2.5),
  elastic: Animator.makeElastic(1),
  veryElastic: Animator.makeElastic(3),
  bouncy: Animator.makeBounce(1),
  veryBouncy: Animator.makeBounce(3)
};

function NumericalStyleSubject(a, c, b, d, f) {
  this.els = Animator.makeArray(a);
  this.property = "opacity" == c && window.ActiveXObject ? "filter" : Animator.camelize(c);
  this.from = parseFloat(b);
  this.to = parseFloat(d);
  this.units = null != f ? f : "px"
}
NumericalStyleSubject.prototype = {
  setState: function(a) {
    a = this.getStyle(a);
    for (var c = 0, b = 0; b < this.els.length; b++) {
      try {
        this.els[b].style[this.property] = a
      } catch (d) {
        if ("fontWeight" != this.property) throw d;
      }
      if (20 < c++) break
    }
  },
  getStyle: function(a) {
    a = this.from + (this.to - this.from) * a;
    return "filter" == this.property ? "alpha(opacity=" + Math.round(100 * a) + ")" : "opacity" == this.property ? a : Math.round(a) + this.units
  },
  inspect: function() {
    return "\t" + this.property + "(" + this.from + this.units + " to " + this.to + this.units + ")\n"
  }
};

function ColorStyleSubject(a, c, b, d) {
  this.els = Animator.makeArray(a);
  this.property = Animator.camelize(c);
  this.to = this.expandColor(d);
  this.from = this.expandColor(b);
  this.origFrom = b;
  this.origTo = d
}
ColorStyleSubject.prototype = {
  expandColor: function(a) {
    var c, b;
    if (c = ColorStyleSubject.parseColor(a)) return a = parseInt(c.slice(1, 3), 16), b = parseInt(c.slice(3, 5), 16), c = parseInt(c.slice(5, 7), 16), [a, b, c];
    window.DEBUG && alert("Invalid colour: '" + a + "'")
  },
  getValueForState: function(a, c) {
    return Math.round(this.from[a] + (this.to[a] - this.from[a]) * c)
  },
  setState: function(a) {
    a = "#" + ColorStyleSubject.toColorPart(this.getValueForState(0, a)) + ColorStyleSubject.toColorPart(this.getValueForState(1, a)) + ColorStyleSubject.toColorPart(this.getValueForState(2,
    a));
    for (var c = 0; c < this.els.length; c++) this.els[c].style[this.property] = a
  },
  inspect: function() {
    return "\t" + this.property + "(" + this.origFrom + " to " + this.origTo + ")\n"
  }
};
ColorStyleSubject.parseColor = function(a) {
  var c = "#",
    b;
  if (b = ColorStyleSubject.parseColor.rgbRe.exec(a)) {
    for (var d = 1; 3 >= d; d++) a = Math.max(0, Math.min(255, parseInt(b[d]))), c += ColorStyleSubject.toColorPart(a);
    return c
  }
  if (b = ColorStyleSubject.parseColor.hexRe.exec(a)) {
    if (3 == b[1].length) {
      for (d = 0; 3 > d; d++) c += b[1].charAt(d) + b[1].charAt(d);
      return c
    }
    return "#" + b[1]
  }
  return !1
};
ColorStyleSubject.toColorPart = function(a) {
  255 < a && (a = 255);
  var c = a.toString(16);
  return 16 > a ? "0" + c : c
};
ColorStyleSubject.parseColor.rgbRe = /^rgb\(\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*\)$/i;
ColorStyleSubject.parseColor.hexRe = /^\#([0-9a-fA-F]{3}|[0-9a-fA-F]{6})$/;

function DiscreteStyleSubject(a, c, b, d, f) {
  this.els = Animator.makeArray(a);
  this.property = Animator.camelize(c);
  this.from = b;
  this.to = d;
  this.threshold = f || 0.5
}
DiscreteStyleSubject.prototype = {
  setState: function(a) {
    for (var c = 0; c < this.els.length; c++) this.els[c].style[this.property] = a <= this.threshold ? this.from : this.to
  },
  inspect: function() {
    return "\t" + this.property + "(" + this.from + " to " + this.to + " @ " + this.threshold + ")\n"
  }
};

function CSSStyleSubject(a, c, b) {
  a = Animator.makeArray(a);
  this.subjects = [];
  if (0 != a.length) {
    var d;
    if (b) c = this.parseStyle(c, a[0]), b = this.parseStyle(b, a[0]);
    else for (d in b = this.parseStyle(c, a[0]), c = {}, b) c[d] = CSSStyleSubject.getStyle(a[0], d);
    for (d in c) c[d] == b[d] && (delete c[d], delete b[d]);
    var f, e, h, g;
    for (d in c) {
      var k = String(c[d]),
        m = String(b[d]);
      if (null == b[d]) window.DEBUG && alert("No to style provided for '" + d + '"');
      else {
        if (h = ColorStyleSubject.parseColor(k)) g = ColorStyleSubject.parseColor(m), e = ColorStyleSubject;
        else if (k.match(CSSStyleSubject.numericalRe) && m.match(CSSStyleSubject.numericalRe)) h = parseFloat(k), g = parseFloat(m), e = NumericalStyleSubject, f = CSSStyleSubject.numericalRe.exec(k), m = CSSStyleSubject.numericalRe.exec(m), f = null != f[1] ? f[1] : null != m[1] ? m[1] : m;
        else if (k.match(CSSStyleSubject.discreteRe) && m.match(CSSStyleSubject.discreteRe)) h = k, g = m, e = DiscreteStyleSubject, f = 0;
        else {
          window.DEBUG && alert("Unrecognised format for value of " + d + ": '" + c[d] + "'");
          continue
        }
        this.subjects[this.subjects.length] = new e(a, d,
        h, g, f)
      }
    }
  }
}
CSSStyleSubject.prototype = {
  parseStyle: function(a, c) {
    var b = {};
    if (-1 != a.indexOf(":")) for (var d = a.split(";"), f = 0; f < d.length; f++) {
      var e = CSSStyleSubject.ruleRe.exec(d[f]);
      e && (b[e[1]] = e[2])
    } else {
      var h;
      h = c.className;
      c.className = a;
      for (f = 0; f < CSSStyleSubject.cssProperties.length; f++) d = CSSStyleSubject.cssProperties[f], e = CSSStyleSubject.getStyle(c, d), null != e && (b[d] = e);
      c.className = h
    }
    return b
  },
  setState: function(a) {
    for (var c = 0; c < this.subjects.length; c++) this.subjects[c].setState(a)
  },
  inspect: function() {
    for (var a = "",
    c = 0; c < this.subjects.length; c++) a += this.subjects[c].inspect();
    return a
  }
};
CSSStyleSubject.getStyle = function(a, c) {
  var b;
  if (document.defaultView && document.defaultView.getComputedStyle && (b = document.defaultView.getComputedStyle(a, "").getPropertyValue(c))) return b;
  c = Animator.camelize(c);
  a.currentStyle && (b = a.currentStyle[c]);
  return b || a.style[c]
};
CSSStyleSubject.ruleRe = /^\s*([a-zA-Z\-]+)\s*:\s*(\S(.+\S)?)\s*$/;
CSSStyleSubject.numericalRe = /^-?\d+(?:\.\d+)?(%|[a-zA-Z]{2})?$/;
CSSStyleSubject.discreteRe = /^\w+$/;
CSSStyleSubject.cssProperties = "azimuth background background-attachment background-color background-image background-position background-repeat border-collapse border-color border-spacing border-style border-top border-top-color border-right-color border-bottom-color border-left-color border-top-style border-right-style border-bottom-style border-left-style border-top-width border-right-width border-bottom-width border-left-width border-width bottom clear clip color content cursor direction display elevation empty-cells css-float font font-family font-size font-size-adjust font-stretch font-style font-variant font-weight height left letter-spacing line-height list-style list-style-image list-style-position list-style-type margin margin-top margin-right margin-bottom margin-left max-height max-width min-height min-width orphans outline outline-color outline-style outline-width overflow padding padding-top padding-right padding-bottom padding-left pause position right size table-layout text-align text-decoration text-indent text-shadow text-transform top vertical-align visibility white-space width word-spacing z-index opacity outline-offset overflow-x overflow-y".split(" ");

function AnimatorChain(a, c) {
  this.animators = a;
  this.setOptions(c);
  for (var b = 0; b < this.animators.length; b++) this.listenTo(this.animators[b]);
  this.forwards = !1;
  this.current = 0
}
AnimatorChain.prototype = {
  setOptions: function(a) {
    this.options = Animator.applyDefaults({
      resetOnPlay: !0
    }, a)
  },
  play: function() {
    this.forwards = !0;
    this.current = -1;
    if (this.options.resetOnPlay) for (var a = 0; a < this.animators.length; a++) this.animators[a].jumpTo(0);
    this.advance()
  },
  reverse: function() {
    this.forwards = !1;
    this.current = this.animators.length;
    if (this.options.resetOnPlay) for (var a = 0; a < this.animators.length; a++) this.animators[a].jumpTo(1);
    this.advance()
  },
  toggle: function() {
    this.forwards ? this.seekTo(0) : this.seekTo(1)
  },
  listenTo: function(a) {
    var c = a.options.onComplete,
      b = this;
    a.options.onComplete = function() {
      c && c.call(a);
      b.advance()
    }
  },
  advance: function() {
    this.forwards ? null != this.animators[this.current + 1] && (this.current++, this.animators[this.current].play()) : null != this.animators[this.current - 1] && (this.current--, this.animators[this.current].reverse())
  },
  seekTo: function(a) {
    0 >= a ? (this.forwards = !1, this.animators[this.current].seekTo(0)) : (this.forwards = !0, this.animators[this.current].seekTo(1))
  }
};

function Accordion(a) {
  this.setOptions(a);
  a = this.options.initialSection;
  var c;
  this.options.rememberance && (c = document.location.hash.substring(1));
  this.rememberanceTexts = [];
  this.ans = [];
  for (var b = this, d = 0; d < this.options.sections.length; d++) {
    var f = this.options.sections[d],
      e = new Animator(this.options.animatorOptions);
    e.addSubject(new NumericalStyleSubject(f, this.options.property, this.options.from + this.options.shift * d, this.options.to + this.options.shift * d, this.options.units));
    e.jumpTo(0);
    f = this.options.getActivator(f);
    f.index = d;
    f.onclick = function() {
      b.show(this.index)
    };
    this.ans[this.ans.length] = e;
    this.rememberanceTexts[d] = f.innerHTML.replace(/\s/g, "");
    this.rememberanceTexts[d] === c && (a = d)
  }
  this.show(a)
}
Accordion.prototype = {
  setOptions: function(a) {
    this.options = Object.extend({
      sections: null,
      getActivator: function(a) {
        return document.getElementById(a.getAttribute("activator"))
      },
      shift: 0,
      initialSection: 0,
      rememberance: !0,
      animatorOptions: {}
    }, a || {})
  },
  show: function(a) {
    for (var c = 0; c < this.ans.length; c++) this.ans[c].seekTo(c > a ? 1 : 0);
    this.options.rememberance && (document.location.hash = this.rememberanceTexts[a])
  }
};
var threeSixtyPlayer, ThreeSixtyPlayer;
(function(a) {
  function c() {
    var b = this,
      c = this,
      f = soundManager,
      e = navigator.userAgent,
      h = e.match(/msie/i),
      g = e.match(/opera/i),
      k = e.match(/safari/i),
      m = e.match(/chrome/i);
    e.match(/firefox/i);
    var p = e.match(/ipad|iphone/i),
      q = "undefined" === typeof a.G_vmlCanvasManager && "undefined" !== typeof document.createElement("canvas").getContext("2d"),
      n = g || m ? 359.9 : 360;
    this.excludeClass = "threesixty-exclude";
    this.links = [];
    this.sounds = [];
    this.soundsByURL = [];
    this.indexByURL = [];
    this.lastTouchedSound = this.lastSound = null;
    this.soundCount = 0;
    this.vuMeter = this.oUIImageMap = this.oUITemplate = null;
    this.callbackCount = 0;
    this.peakDataHistory = [];
    this.config = {
      playNext: !1,
      autoPlay: !1,
      allowMultiple: !1,
      loadRingColor: "#ccc",
      playRingColor: "#000",
      backgroundRingColor: "#eee",
      segmentRingColor: "rgba(255,255,255,0.33)",
      segmentRingColorAlt: "rgba(0,0,0,0.1)",
      loadRingColorMetadata: "#ddd",
      playRingColorMetadata: "rgba(128,192,256,0.9)",
      circleDiameter: null,
      circleRadius: null,
      animDuration: 500,
      animTransition: a.Animator.tx.bouncy,
      showHMSTime: !1,
      scaleFont: !0,
      useWaveformData: !1,
      waveformDataColor: "#0099ff",
      waveformDataDownsample: 3,
      waveformDataOutside: !1,
      waveformDataConstrain: !1,
      waveformDataLineRatio: 0.64,
      useEQData: !1,
      eqDataColor: "#339933",
      eqDataDownsample: 4,
      eqDataOutside: !0,
      eqDataLineRatio: 0.54,
      usePeakData: !0,
      peakDataColor: "#ff33ff",
      peakDataOutside: !0,
      peakDataLineRatio: 0.5,
      useAmplifier: !0,
      fontSizeMax: null,
      scaleArcWidth: 1,
      useFavIcon: !1
    };
    this.css = {
      sDefault: "sm2_link",
      sBuffering: "sm2_buffering",
      sPlaying: "sm2_playing",
      sPaused: "sm2_paused"
    };
    this.addEventHandler = "undefined" !== typeof a.addEventListener ? function(b, a, c) {
      return b.addEventListener(a, c, !1)
    } : function(b, a, c) {
      b.attachEvent("on" + a, c)
    };
    this.removeEventHandler = "undefined" !== typeof a.removeEventListener ? function(b, a, c) {
      return b.removeEventListener(a, c, !1)
    } : function(b, a, c) {
      return b.detachEvent("on" + a, c)
    };
    this.hasClass = function(b, a) {
      return "undefined" !== typeof b.className ? b.className.match(RegExp("(\\s|^)" + a + "(\\s|$)")) : !1
    };
    this.addClass = function(a, c) {
      if (!a || !c || b.hasClass(a, c)) return !1;
      a.className = (a.className ? a.className +
        " " : "") + c
    };
    this.removeClass = function(a, c) {
      if (!a || !c || !b.hasClass(a, c)) return !1;
      a.className = a.className.replace(RegExp("( " + c + ")|(" + c + ")", "g"), "")
    };
    this.getElementsByClassName = function(a, c, d) {
      var e = d || document;
      d = [];
      var f, g = [];
      if ("undefined" !== typeof c && "string" !== typeof c) for (f = c.length; f--;) {
        if (!g || !g[c[f]]) g[c[f]] = e.getElementsByTagName(c[f])
      } else g = c ? e.getElementsByTagName(c) : e.all || e.getElementsByTagName("*");
      if ("string" !== typeof c) for (f = c.length; f--;) for (e = g[c[f]].length; e--;) b.hasClass(g[c[f]][e],
      a) && d.push(g[c[f]][e]);
      else for (f = 0; f < g.length; f++) b.hasClass(g[f], a) && d.push(g[f]);
      return d
    };
    this.getParentByNodeName = function(b, a) {
      if (!b || !a) return !1;
      for (a = a.toLowerCase(); b.parentNode && a !== b.parentNode.nodeName.toLowerCase();) b = b.parentNode;
      return b.parentNode && a === b.parentNode.nodeName.toLowerCase() ? b.parentNode : null
    };
    this.getParentByClassName = function(a, c) {
      if (!a || !c) return !1;
      for (; a.parentNode && !b.hasClass(a.parentNode, c);) a = a.parentNode;
      return a.parentNode && b.hasClass(a.parentNode, c) ? a.parentNode : null
    };
    this.getSoundByURL = function(a) {
      return "undefined" !== typeof b.soundsByURL[a] ? b.soundsByURL[a] : null
    };
    this.isChildOfNode = function(b, a) {
      if (!b || !b.parentNode) return !1;
      a = a.toLowerCase();
      do b = b.parentNode;
      while (b && b.parentNode && b.nodeName.toLowerCase() !== a);
      return b && b.nodeName.toLowerCase() === a ? b : null
    };
    this.isChildOfClass = function(a, c) {
      if (!a || !c) return !1;
      for (; a.parentNode && !b.hasClass(a, c);) a = b.findParent(a);
      return b.hasClass(a, c)
    };
    this.findParent = function(b) {
      if (!b || !b.parentNode) return !1;
      b = b.parentNode;
      if (2 === b.nodeType) for (; b && b.parentNode && 2 === b.parentNode.nodeType;) b = b.parentNode;
      return b
    };
    this.getStyle = function(b, c) {
      try {
        if (b.currentStyle) return b.currentStyle[c];
        if (a.getComputedStyle) return document.defaultView.getComputedStyle(b, null).getPropertyValue(c)
      } catch (d) {}
      return null
    };
    this.findXY = function(b) {
      var a = 0,
        c = 0;
      do a += b.offsetLeft, c += b.offsetTop;
      while (b = b.offsetParent);
      return [a, c]
    };
    this.getMouseXY = function(c) {
      c = c ? c : a.event;
      p && c.touches && (c = c.touches[0]);
      if (c.pageX || c.pageY) return [c.pageX, c.pageY];
      if (c.clientX || c.clientY) return [c.clientX + b.getScrollLeft(), c.clientY + b.getScrollTop()]
    };
    this.getScrollLeft = function() {
      return document.body.scrollLeft + document.documentElement.scrollLeft
    };
    this.getScrollTop = function() {
      return document.body.scrollTop + document.documentElement.scrollTop
    };
    this.events = {
      play: function() {
        c.removeClass(this._360data.oUIBox, this._360data.className);
        this._360data.className = c.css.sPlaying;
        c.addClass(this._360data.oUIBox, this._360data.className);
        b.fanOut(this)
      },
      stop: function() {
        c.removeClass(this._360data.oUIBox,
        this._360data.className);
        this._360data.className = "";
        b.fanIn(this)
      },
      pause: function() {
        c.removeClass(this._360data.oUIBox, this._360data.className);
        this._360data.className = c.css.sPaused;
        c.addClass(this._360data.oUIBox, this._360data.className)
      },
      resume: function() {
        c.removeClass(this._360data.oUIBox, this._360data.className);
        this._360data.className = c.css.sPlaying;
        c.addClass(this._360data.oUIBox, this._360data.className)
      },
      finish: function() {
        var a;
        c.removeClass(this._360data.oUIBox, this._360data.className);
        this._360data.className =
          "";
        this._360data.didFinish = !0;
        b.fanIn(this);
        c.config.playNext && (a = c.indexByURL[this._360data.oLink.href] + 1, a < c.links.length && c.handleClick({
          target: c.links[a]
        }))
      },
      whileloading: function() {
        this.paused && b.updatePlaying.apply(this)
      },
      whileplaying: function() {
        b.updatePlaying.apply(this);
        this._360data.fps++
      },
      bufferchange: function() {
        this.isBuffering ? c.addClass(this._360data.oUIBox, c.css.sBuffering) : c.removeClass(this._360data.oUIBox, c.css.sBuffering)
      }
    };
    this.stopEvent = function(b) {
      "undefined" !== typeof b && "undefined" !== typeof b.preventDefault ? b.preventDefault() : "undefined" !== typeof a.event && "undefined" !== typeof a.event.returnValue && (a.event.returnValue = !1);
      return !1
    };
    this.getTheDamnLink = h ? function(b) {
      return b && b.target ? b.target : a.event.srcElement
    } : function(b) {
      return b.target
    };
    this.handleClick = function(c) {
      if (1 < c.button) return !0;
      var d = b.getTheDamnLink(c),
        e, g, k, h, m, p;
      if ("a" !== d.nodeName.toLowerCase() && (d = b.isChildOfNode(d, "a"), !d) || !b.isChildOfClass(d, "ui360")) return !0;
      d.getAttribute("href");
      if (!d.href || !f.canPlayLink(d) || b.hasClass(d, b.excludeClass)) return !0;
      f._writeDebug("handleClick()");
      g = d.href;
      (k = b.getSoundByURL(g)) ? k === b.lastSound ? k.togglePause() : (k.togglePause(), f._writeDebug("sound different than last sound: " + b.lastSound.id), !b.config.allowMultiple && b.lastSound && b.stopSound(b.lastSound)) : (h = d.parentNode, m = b.getElementsByClassName("ui360-vis", "div", h.parentNode).length, k = f.createSound({
        id: "ui360Sound" + b.soundCount++,
        url: g,
        onplay: b.events.play,
        onstop: b.events.stop,
        onpause: b.events.pause,
        onresume: b.events.resume,
        onfinish: b.events.finish,
        onbufferchange: b.events.bufferchange,
        type: d.type || null,
        whileloading: b.events.whileloading,
        whileplaying: b.events.whileplaying,
        useWaveformData: m && b.config.useWaveformData,
        useEQData: m && b.config.useEQData,
        usePeakData: m && b.config.usePeakData
      }), p = parseInt(b.getElementsByClassName("sm2-360ui", "div", h)[0].offsetWidth, 10), e = b.getElementsByClassName("sm2-canvas", "canvas", h), k._360data = {
        oUI360: b.getParentByClassName(d, "ui360"),
        oLink: d,
        className: b.css.sPlaying,
        oUIBox: b.getElementsByClassName("sm2-360ui",
          "div", h)[0],
        oCanvas: e[e.length - 1],
        oButton: b.getElementsByClassName("sm2-360btn", "span", h)[0],
        oTiming: b.getElementsByClassName("sm2-timing", "div", h)[0],
        oCover: b.getElementsByClassName("sm2-cover", "div", h)[0],
        circleDiameter: p,
        circleRadius: p / 2,
        lastTime: null,
        didFinish: null,
        pauseCount: 0,
        radius: 0,
        fontSize: 1,
        fontSizeMax: b.config.fontSizeMax,
        scaleFont: m && b.config.scaleFont,
        showHMSTime: m,
        amplifier: m && b.config.usePeakData ? 0.9 : 1,
        radiusMax: 0.175 * p,
        width: 0,
        widthMax: 0.4 * p,
        lastValues: {
          bytesLoaded: 0,
          bytesTotal: 0,
          position: 0,
          durationEstimate: 0
        },
        animating: !1,
        oAnim: new a.Animator({
          duration: b.config.animDuration,
          transition: b.config.animTransition,
          onComplete: function() {}
        }),
        oAnimProgress: function(a) {
          this._360data.radius = parseInt(this._360data.radiusMax * this._360data.amplifier * a, 10);
          this._360data.width = parseInt(this._360data.widthMax * this._360data.amplifier * a, 10);
          this._360data.scaleFont && null !== this._360data.fontSizeMax && (this._360data.oTiming.style.fontSize = parseInt(Math.max(1, this._360data.fontSizeMax * a), 10) + "px",
          this._360data.oTiming.style.opacity = a);
          (this.paused || 0 === this.playState || 0 === this._360data.lastValues.bytesLoaded || 0 === this._360data.lastValues.position) && b.updatePlaying.apply(this)
        },
        fps: 0
      }, "undefined" !== typeof b.Metadata && b.getElementsByClassName("metadata", "div", k._360data.oUI360).length && (k._360data.metadata = new b.Metadata(k, b)), k._360data.scaleFont && null !== k._360data.fontSizeMax && (k._360data.oTiming.style.fontSize = "1px"), k._360data.oAnim.addSubject(k._360data.oAnimProgress, k), b.refreshCoords(k),
      b.updatePlaying.apply(k), b.soundsByURL[g] = k, b.sounds.push(k), !b.config.allowMultiple && b.lastSound && b.stopSound(b.lastSound), k.play());
      b.lastSound = k;
      "undefined" !== typeof c && "undefined" !== typeof c.preventDefault ? c.preventDefault() : "undefined" !== typeof a.event && (a.event.returnValue = !1);
      return !1
    };
    this.fanOut = function(c) {
      if (1 === c._360data.animating) return !1;
      c._360data.animating = 0;
      soundManager._writeDebug("fanOut: " + c.id + ": " + c._360data.oLink.href);
      c._360data.oAnim.seekTo(1);
      a.setTimeout(function() {
        c._360data.animating = 0
      }, b.config.animDuration + 20)
    };
    this.fanIn = function(c) {
      if (-1 === c._360data.animating) return !1;
      c._360data.animating = -1;
      soundManager._writeDebug("fanIn: " + c.id + ": " + c._360data.oLink.href);
      c._360data.oAnim.seekTo(0);
      a.setTimeout(function() {
        c._360data.didFinish = !1;
        c._360data.animating = 0;
        b.resetLastValues(c)
      }, b.config.animDuration + 20)
    };
    this.resetLastValues = function(b) {
      b._360data.lastValues.position = 0
    };
    this.refreshCoords = function(a) {
      a._360data.canvasXY = b.findXY(a._360data.oCanvas);
      a._360data.canvasMid = [a._360data.circleRadius,
      a._360data.circleRadius];
      a._360data.canvasMidXY = [a._360data.canvasXY[0] + a._360data.canvasMid[0], a._360data.canvasXY[1] + a._360data.canvasMid[1]]
    };
    this.stopSound = function(b) {
      soundManager._writeDebug("stopSound: " + b.id);
      soundManager.stop(b.id);
      p || soundManager.unload(b.id)
    };
    this.buttonClick = function(c) {
      b.handleClick({
        target: b.getParentByClassName(c ? c.target ? c.target : c.srcElement : a.event.srcElement, "sm2-360ui").nextSibling
      });
      return !1
    };
    this.buttonMouseDown = function(a) {
      p ? b.addEventHandler(document, "touchmove",
      b.mouseDown) : document.onmousemove = function(a) {
        b.mouseDown(a)
      };
      b.stopEvent(a);
      return !1
    };
    this.mouseDown = function(c) {
      if (!p && 1 < c.button) return !0;
      if (!b.lastSound) return b.stopEvent(c), !1;
      var d = c ? c : a.event;
      p && d.touches && (d = d.touches[0]);
      d = b.getSoundByURL(b.getElementsByClassName("sm2_link", "a", b.getParentByClassName(d.target || d.srcElement, "ui360"))[0].href);
      b.lastTouchedSound = d;
      b.refreshCoords(d);
      d = d._360data;
      b.addClass(d.oUIBox, "sm2_dragging");
      d.pauseCount = b.lastTouchedSound.paused ? 1 : 0;
      b.mmh(c ? c : a.event);
      p ? (b.removeEventHandler(document, "touchmove", b.mouseDown), b.addEventHandler(document, "touchmove", b.mmh), b.addEventHandler(document, "touchend", b.mouseUp)) : (document.onmousemove = b.mmh, document.onmouseup = b.mouseUp);
      b.stopEvent(c);
      return !1
    };
    this.mouseUp = function(a) {
      a = b.lastTouchedSound._360data;
      b.removeClass(a.oUIBox, "sm2_dragging");
      0 === a.pauseCount && b.lastTouchedSound.resume();
      p ? (b.removeEventHandler(document, "touchmove", b.mmh), b.removeEventHandler(document, "touchend", b.mouseUP)) : (document.onmousemove = null, document.onmouseup = null)
    };
    this.mmh = function(c) {
      "undefined" === typeof c && (c = a.event);
      var d = b.lastTouchedSound,
        e = b.getMouseXY(c),
        e = Math.floor(n - (b.rad2deg(Math.atan2(e[0] - d._360data.canvasMidXY[0], e[1] - d._360data.canvasMidXY[1])) + 180));
      d.setPosition(d.durationEstimate * (e / n));
      b.stopEvent(c);
      return !1
    };
    this.drawSolidArc = function(a, c, d, e, f, h, m) {
      var p = a,
        n;
      p.getContext && (n = p.getContext("2d"));
      a = n;
      m || b.clearCanvas(p);
      c && (n.fillStyle = c);
      a.beginPath();
      isNaN(f) && (f = 0);
      c = d - e;
      e = g || k;
      if (!e || e && 0 < d) a.arc(0, 0,
      d, h, f, !1), d = b.getArcEndpointCoords(c, f), a.lineTo(d.x, d.y), a.arc(0, 0, c, f, h, !0), a.closePath(), a.fill()
    };
    this.getArcEndpointCoords = function(a, b) {
      return {
        x: a * Math.cos(b),
        y: a * Math.sin(b)
      }
    };
    this.deg2rad = function(a) {
      return a * Math.PI / 180
    };
    this.rad2deg = function(a) {
      return 180 * a / Math.PI
    };
    this.getTime = function(a, b) {
      var c = Math.floor(a / 1E3),
        d = Math.floor(c / 60),
        c = c - 60 * d;
      return b ? d + ":" + (10 > c ? "0" + c : c) : {
        min: d,
        sec: c
      }
    };
    this.clearCanvas = function(a) {
      var b = null,
        c;
      a.getContext && (b = a.getContext("2d"));
      b && (c = a.offsetWidth, a = a.offsetHeight, b.clearRect(-(c / 2), -(a / 2), c, a))
    };
    this.updatePlaying = function() {
      var a = this._360data.showHMSTime ? b.getTime(this.position, !0) : parseInt(this.position / 1E3, 10),
        c = b.config.scaleArcWidth;
      this.bytesLoaded && (this._360data.lastValues.bytesLoaded = this.bytesLoaded, this._360data.lastValues.bytesTotal = this.bytesTotal);
      this.position && (this._360data.lastValues.position = this.position);
      this.durationEstimate && (this._360data.lastValues.durationEstimate = this.durationEstimate);
      b.drawSolidArc(this._360data.oCanvas,
      b.config.backgroundRingColor, this._360data.width, this._360data.radius * c, b.deg2rad(n), !1);
      b.drawSolidArc(this._360data.oCanvas, this._360data.metadata ? b.config.loadRingColorMetadata : b.config.loadRingColor, this._360data.width, this._360data.radius * c, b.deg2rad(n * (this._360data.lastValues.bytesLoaded / this._360data.lastValues.bytesTotal)), 0, !0);
      0 !== this._360data.lastValues.position && b.drawSolidArc(this._360data.oCanvas, this._360data.metadata ? b.config.playRingColorMetadata : b.config.playRingColor, this._360data.width,
      this._360data.radius * c, b.deg2rad(1 === this._360data.didFinish ? n : n * (this._360data.lastValues.position / this._360data.lastValues.durationEstimate)), 0, !0);
      this._360data.metadata && this._360data.metadata.events.whileplaying();
      a !== this._360data.lastTime && (this._360data.lastTime = a, this._360data.oTiming.innerHTML = a);
      (this.instanceOptions.useWaveformData || this.instanceOptions.useEQData) && q && b.updateWaveform(this);
      b.config.useFavIcon && b.vuMeter && b.vuMeter.updateVU(this)
    };
    this.updateWaveform = function(a) {
      if (!b.config.useWaveformData && !b.config.useEQData || !f.features.waveformData && !f.features.eqData || !a.waveformData.left.length && !a.eqData.length && !a.peakData.left) return !1;
      a._360data.oCanvas.getContext("2d");
      var c = parseInt(a._360data.circleDiameter / 2, 10) / 2,
        d, e, g, k, h, m, p, n, q;
      if (b.config.useWaveformData) {
        g = b.config.waveformDataDownsample;
        g = Math.max(1, g);
        k = 256 / g;
        p = b.config.waveformDataOutside ? 1 : b.config.waveformDataConstrain ? 0.5 : 0.565;
        c = b.config.waveformDataOutside ? 0.7 : 0.75;
        n = b.deg2rad(360 / k * b.config.waveformDataLineRatio);
        for (d = 0; 256 > d; d += g) h = b.deg2rad(360 * (1 * (d / k) / g)), m = h + n, e = a.waveformData.left[d], 0 > e && b.config.waveformDataConstrain && (e = Math.abs(e)), b.drawSolidArc(a._360data.oCanvas, b.config.waveformDataColor, a._360data.width * p * (2 - b.config.scaleArcWidth), 1.25 * (a._360data.radius * c) * e, m, h, !0)
      }
      if (b.config.useEQData) {
        g = b.config.eqDataDownsample;
        g = Math.max(1, g);
        k = 192;
        p = b.config.eqDataOutside ? 1 : 0.565;
        e = b.config.eqDataOutside ? -1 : 1;
        c = b.config.eqDataOutside ? 0.5 : 0.75;
        n = b.deg2rad(360 / (k / g) * b.config.eqDataLineRatio);
        q = b.deg2rad(1 === a._360data.didFinish ? 360 : 360 * (a._360data.lastValues.position / a._360data.lastValues.durationEstimate));
        for (d = 0; d < k; d += g) h = b.deg2rad(360 * (d / k)), m = h + n, b.drawSolidArc(a._360data.oCanvas, m > q ? b.config.eqDataColor : b.config.playRingColor, a._360data.width * p, a._360data.radius * c * a.eqData.left[d] * e, m, h, !0)
      }
      if (b.config.usePeakData && !a._360data.animating) {
        c = a.peakData.left || a.peakData.right;
        k = 3;
        for (d = 0; d < k; d++) c = c || a.eqData[d];
        a._360data.amplifier = b.config.useAmplifier ? 0.9 + 0.1 * c : 1;
        a._360data.radiusMax = 0.175 * a._360data.circleDiameter * a._360data.amplifier;
        a._360data.widthMax = 0.4 * a._360data.circleDiameter * a._360data.amplifier;
        a._360data.radius = parseInt(a._360data.radiusMax * a._360data.amplifier, 10);
        a._360data.width = parseInt(a._360data.widthMax * a._360data.amplifier, 10)
      }
    };
    this.getUIHTML = function(a) {
      return ['<canvas class="sm2-canvas" width="' + a + '" height="' + a + '"></canvas>', ' <span class="sm2-360btn sm2-360btn-default"></span>', ' <div class="sm2-timing' + (navigator.userAgent.match(/safari/i) ? " alignTweak" : "") + '"></div>', ' <div class="sm2-cover"></div>']
    };
    this.uiTest = function(a) {
      var c = document.createElement("div"),
        d, e;
      c.className = "sm2-360ui";
      d = document.createElement("div");
      d.className = "ui360" + (a ? " " + a : "");
      c = d.appendChild(c.cloneNode(!0));
      d.style.position = "absolute";
      d.style.left = "-9999px";
      a = document.body.appendChild(d);
      e = b.getUIHTML(c.offsetWidth);
      c.innerHTML = e[1] + e[2] + e[3];
      c = parseInt(c.offsetWidth, 10);
      e = parseInt(c / 2, 10);
      a = b.getElementsByClassName("sm2-timing", "div", a)[0];
      a = parseInt(b.getStyle(a, "font-size"), 10);
      isNaN(a) && (a = null);
      d.parentNode.removeChild(d);
      return {
        circleDiameter: c,
        circleRadius: e,
        fontSizeMax: a
      }
    };
    this.init = function() {
      f._writeDebug("threeSixtyPlayer.init()");
      var c = b.getElementsByClassName("ui360", "div"),
        d, e, g = [],
        k = !1,
        m = 0,
        n, q, t, s, w;
      d = 0;
      for (e = c.length; d < e; d++) g.push(c[d].getElementsByTagName("a")[0]), c[d].style.backgroundImage = "none";
      b.oUITemplate = document.createElement("div");
      b.oUITemplate.className = "sm2-360ui";
      b.oUITemplateVis = document.createElement("div");
      b.oUITemplateVis.className = "sm2-360ui";
      q = b.uiTest();
      b.config.circleDiameter = q.circleDiameter;
      b.config.circleRadius = q.circleRadius;
      t = b.uiTest("ui360-vis");
      b.config.fontSizeMax = t.fontSizeMax;
      b.oUITemplate.innerHTML = b.getUIHTML(b.config.circleDiameter).join("");
      b.oUITemplateVis.innerHTML = b.getUIHTML(t.circleDiameter).join("");
      d = 0;
      for (e = g.length; d < e; d++) f.canPlayLink(g[d]) && (!b.hasClass(g[d], b.excludeClass) && !b.hasClass(g[d], b.css.sDefault)) && (b.addClass(g[d], b.css.sDefault), b.links[m] = g[d], b.indexByURL[g[d].href] = m, m++, k = b.hasClass(g[d].parentNode, "ui360-vis"), n = (k ? t : q).circleDiameter, c = (k ? t : q).circleRadius, k = g[d].parentNode.insertBefore((k ? b.oUITemplateVis : b.oUITemplate).cloneNode(!0), g[d]), h && "undefined" !== typeof a.G_vmlCanvasManager ? (s = document.createElement("canvas"), s.className = "sm2-canvas", w = "sm2_canvas_" + d + (new Date).getTime(), s.id = w, s.width = n, s.height = n, k.appendChild(s), a.G_vmlCanvasManager.initElement(s), n = document.getElementById(w), k = n.parentNode.getElementsByTagName("canvas"), 1 < k.length && (n = k[k.length - 1])) : n = g[d].parentNode.getElementsByTagName("canvas")[0], k = b.getElementsByClassName("sm2-cover",
        "div", g[d].parentNode)[0], s = g[d].parentNode.getElementsByTagName("span")[0], b.addEventHandler(s, "click", b.buttonClick), p ? b.addEventHandler(k, "touchstart", b.mouseDown) : b.addEventHandler(k, "mousedown", b.mouseDown), n = n.getContext("2d"), n.translate(c, c), n.rotate(b.deg2rad(-90)));
      0 < m && (b.addEventHandler(document, "click", b.handleClick), b.config.autoPlay && b.handleClick({
        target: b.links[0],
        preventDefault: function() {}
      }));
      f._writeDebug("threeSixtyPlayer.init(): Found " + m + " relevant items.");
      b.config.useFavIcon &&
        "undefined" !== typeof this.VUMeter && (this.vuMeter = new this.VUMeter(this))
    }
  }
  c.prototype.VUMeter = function(a) {
    var c = this,
      f = document.getElementsByTagName("head")[0],
      e = navigator.userAgent.match(/opera/i),
      h = navigator.userAgent.match(/firefox/i);
    this.vuMeterData = [];
    this.vuDataCanvas = null;
    this.setPageIcon = function(c) {
      if (!a.config.useFavIcon || !a.config.usePeakData || !c) return !1;
      var d = document.getElementById("sm2-favicon");
      d && (f.removeChild(d), d = null);
      d || (d = document.createElement("link"), d.id = "sm2-favicon", d.rel =
        "shortcut icon", d.type = "image/png", d.href = c, document.getElementsByTagName("head")[0].appendChild(d))
    };
    this.resetPageIcon = function() {
      if (!a.config.useFavIcon) return !1;
      var c = document.getElementById("favicon");
      c && (c.href = "/favicon.ico")
    };
    this.updateVU = function(e) {
      9 <= soundManager.flashVersion && (a.config.useFavIcon && a.config.usePeakData) && c.setPageIcon(c.vuMeterData[parseInt(16 * e.peakData.left, 10)][parseInt(16 * e.peakData.right, 10)])
    };
    this.createVUData = function() {
      var a = 0,
        b = 0,
        e = c.vuDataCanvas.getContext("2d"),
        f = e.createLinearGradient(0, 16, 0, 0),
        h = e.createLinearGradient(0, 16, 0, 0);
      f.addColorStop(0, "rgb(0,192,0)");
      f.addColorStop(0.3, "rgb(0,255,0)");
      f.addColorStop(0.625, "rgb(255,255,0)");
      f.addColorStop(0.85, "rgb(255,0,0)");
      h.addColorStop(0, "rgba(0,0,0,0.2)");
      h.addColorStop(1, "rgba(0,0,0,0.5)");
      for (a = 0; 16 > a; a++) c.vuMeterData[a] = [];
      for (a = 0; 16 > a; a++) for (b = 0; 16 > b; b++) c.vuDataCanvas.setAttribute("width", 16), c.vuDataCanvas.setAttribute("height", 16), e.fillStyle = h, e.fillRect(0, 0, 7, 15), e.fillRect(8, 0, 7, 15), e.fillStyle = f, e.fillRect(0, 15 - a, 7, 16 - (16 - a)), e.fillRect(8, 15 - b, 7, 16 - (16 - b)), e.clearRect(0, 3, 16, 1), e.clearRect(0, 7, 16, 1), e.clearRect(0, 11, 16, 1), c.vuMeterData[a][b] = c.vuDataCanvas.toDataURL("image/png")
    };
    this.testCanvas = function() {
      var a = document.createElement("canvas"),
        b = null;
      if (!a || "undefined" === typeof a.getContext) return null;
      b = a.getContext("2d");
      if (!b || "function" !== typeof a.toDataURL) return null;
      try {
        a.toDataURL("image/png")
      } catch (c) {
        return null
      }
      return a
    };
    this.init = function() {
      a.config.useFavIcon && (c.vuDataCanvas = c.testCanvas(), c.vuDataCanvas && (h || e) ? c.createVUData() : a.config.useFavIcon = !1)
    };
    this.init()
  };
  c.prototype.Metadata = function(a, c) {
    soundManager._wD("Metadata()");
    var f = this,
      e = a._360data.oUI360,
      h = e.getElementsByTagName("ul")[0].getElementsByTagName("li");
    navigator.userAgent.match(/firefox/i);
    var g;
    this.lastWPExec = 0;
    this.refreshInterval = 250;
    this.totalTime = 0;
    this.events = {
      whileplaying: function() {
        var e = a._360data.width,
          g = a._360data.radius,
          h = a.durationEstimate || 1E3 * f.totalTime,
          q = null,
          n, l;
        n = 0;
        for (l = f.data.length; n < l; n++) q = 0 === n % 2, c.drawSolidArc(a._360data.oCanvas, q ? c.config.segmentRingColorAlt : c.config.segmentRingColor, e, g / 2, c.deg2rad(360 * (f.data[n].endTimeMS / h)), c.deg2rad(360 * ((f.data[n].startTimeMS || 1) / h)), !0);
        e = new Date;
        e - f.lastWPExec > f.refreshInterval && (f.refresh(), f.lastWPExec = e)
      }
    };
    this.refresh = function() {
      var c, d, e = null,
        f = a.position,
        g = a._360data.metadata.data;
      c = 0;
      for (d = g.length; c < d; c++) if (f >= g[c].startTimeMS && f <= g[c].endTimeMS) {
        e = c;
        break
      }
      e !== g.currentItem && e < g.length && (a._360data.oLink.innerHTML = g.mainTitle +
        ' <span class="metadata"><span class="sm2_divider"> | </span><span class="sm2_metadata">' + g[e].title + "</span></span>", g.currentItem = e)
    };
    this.strToTime = function(a) {
      a = a.split(":");
      var b = 0,
        c;
      for (c = a.length; c--;) b += parseInt(a[c], 10) * Math.pow(60, a.length - 1 - c);
      return b
    };
    this.data = [];
    this.data.givenDuration = null;
    this.data.currentItem = null;
    this.data.mainTitle = a._360data.oLink.innerHTML;
    for (g = 0; g < h.length; g++) this.data[g] = {
      o: null,
      title: h[g].getElementsByTagName("p")[0].innerHTML,
      startTime: h[g].getElementsByTagName("span")[0].innerHTML,
      startSeconds: f.strToTime(h[g].getElementsByTagName("span")[0].innerHTML.replace(/[()]/g, "")),
      duration: 0,
      durationMS: null,
      startTimeMS: null,
      endTimeMS: null,
      oNote: null
    };
    e = c.getElementsByClassName("duration", "div", e);
    this.data.givenDuration = e.length ? 1E3 * f.strToTime(e[0].innerHTML) : 0;
    for (g = 0; g < this.data.length; g++) this.data[g].duration = parseInt(this.data[g + 1] ? this.data[g + 1].startSeconds : (f.data.givenDuration ? f.data.givenDuration : a.durationEstimate) / 1E3, 10) - this.data[g].startSeconds, this.data[g].startTimeMS = 1E3 * this.data[g].startSeconds, this.data[g].durationMS = 1E3 * this.data[g].duration, this.data[g].endTimeMS = this.data[g].startTimeMS + this.data[g].durationMS, this.totalTime += this.data[g].duration
  };
  navigator.userAgent.match(/webkit/i) && navigator.userAgent.match(/mobile/i) && soundManager.setup({
    useHTML5Audio: !0
  });
  soundManager.setup({
    html5PollingInterval: 50,
    debugMode: a.location.href.match(/debug=1/i),
    consoleOnly: !0,
    flashVersion: 9,
    useHighPerformance: !0,
    useFlashBlock: !0
  });
  soundManager.debugMode && a.setInterval(function() {
    var b = a.threeSixtyPlayer;
    b && (b.lastSound && b.lastSound._360data.fps && "undefined" === typeof a.isHome) && (soundManager._writeDebug("fps: ~" + b.lastSound._360data.fps), b.lastSound._360data.fps = 0)
  }, 1E3);
  a.ThreeSixtyPlayer = c
})(window);
threeSixtyPlayer = new ThreeSixtyPlayer;
soundManager.onready(threeSixtyPlayer.init);
var IS_CHRISTMAS = document.domain.match(/schillmania.com/i) && 11 == (new Date).getMonth() || window.location.toString().match(/christmas/i);

function _id(a) {
  return document.getElementById(a)
}
getSoundByURL = function(a) {
  return "undefined" != typeof self.soundsByURL[a] ? self.soundsByURL[a] : null
};

function init() {
  for (var a = document.getElementById("main"), c = a.getElementsByTagName("dt"), b = c.length; b--;) 0 == (b + 1) % 2 && utils.addClass(c[b], "alt");
  c = a.getElementsByTagName("dl");
  for (b = c.length; b--;) 0 == (b + 1) % 2 && utils.addClass(c[b], "alt");
  IS_CHRISTMAS && (a = document.body.className.split(" "), a.push("has-lights"), document.body.className = a.join(" "))
}

function Utils() {
  var a = this;
  this.hasClass = function(a, b) {
    return "undefined" != typeof a.className ? RegExp("(^|\\s)" + b + "(\\s|$)").test(a.className) : !1
  };
  this.addClass = function(c, b) {
    if (!c || !b || a.hasClass(c, b)) return !1;
    c.className = (c.className ? c.className + " " : "") + b
  };
  this.removeClass = function(c, b) {
    if (!c || !b || !a.hasClass(c, b)) return !1;
    c.className = c.className.replace(RegExp("( " + b + ")|(" + b + ")", "g"), "")
  };
  this.toggleClass = function(c, b) {
    (a.hasClass(c, b) ? a.removeClass : a.addClass)(c, b)
  };
  this.getElementsByClassName = function(c, b, d) {
    var f = d || document;
    d = [];
    var e, h = [];
    if ("undefined" != typeof b && "string" != typeof b) for (e = b.length; e--;) {
      if (!h || !h[b[e]]) h[b[e]] = f.getElementsByTagName(b[e])
    } else h = b ? f.getElementsByTagName(b) : f.all || f.getElementsByTagName("*");
    if ("string" != typeof b) for (e = b.length; e--;) for (f = h[b[e]].length; f--;) a.hasClass(h[b[e]][f], c) && (d[d.length] = h[b[e]][f]);
    else for (e = 0; e < h.length; e++) a.hasClass(h[e], c) && (d[d.length] = h[e]);
    return d
  };
  this.findParent = function(a) {
    if (!a || !a.parentNode) return !1;
    a = a.parentNode;
    if (2 == a.nodeType) for (; a && a.parentNode && 2 == a.parentNode.nodeType;) a = a.parentNode;
    return a
  };
  this.getOffY = function(a) {
    var b = 0;
    if (a.offsetParent) for (; a.offsetParent;) b += a.offsetTop, a = a.offsetParent;
    else a.y && (b += a.y);
    return b
  };
  this.isChildOfClass = function(c, b) {
    if (!c || !b) return !1;
    for (; c.parentNode && !a.hasClass(c, b);) c = a.findParent(c);
    return a.hasClass(c, b)
  };
  this.getParentByClassName = function(c, b) {
    if (!c || !b) return !1;
    for (b = b.toLowerCase(); c.parentNode && !a.hasClass(c.parentNode, b);) c = a.findParent(c);
    return c.parentNode && a.hasClass(c.parentNode, b) ? c.parentNode : null
  }
}
var utils = new Utils,
  lastSelected = null;

function resetFilter(a) {
  var c = null;
  _id("filter-box").style.display = "none";
  utils.removeClass(_id("main"), "filtered");
  for (var b = utils.getElementsByClassName("f-block", ["div", "dl"], _id("main")), d = b.length; d--;) if (b[d].style.display = "block", c = utils.getParentByClassName(b[d], "columnar", _id("main"))) c.style.display = "block";
  lastSelected && utils.removeClass(lastSelected, "active");
  a && (lastSelected = a);
  return !1
}

function setFilter(a, c) {
  var b = a ? a.target || a.srcElement : event.srcElement;
  utils.addClass(_id("main"), "filtered");
  var d = b.nodeName.toLowerCase();
  if ("a" == d) {
    var f = utils.findParent(b);
    f && "li" == f.nodeName.toLowerCase() && (b = f, d = b.nodeName.toLowerCase())
  }
  var e = "",
    f = utils.getElementsByClassName("f-block", ["div", "dl"], _id("main")),
    h = utils.getElementsByClassName("columnar", "div", _id("main")),
    g = null,
    k = [];
  if ("li" != d || "ignore" == b.className) return !0;
  var m = lastSelected && lastSelected == b && utils.hasClass(lastSelected,
    "active");
  if ("li" == d && m) return "undefined" !== typeof a.preventDefault && a.preventDefault(), resetFilter();
  if ("li" == d) {
    e = b.getElementsByTagName("a").length ? b.getElementsByTagName("a")[0].innerHTML : b.innerHTML;
    e = c + e.substr(0, -1 != e.indexOf("(") ? e.indexOf("(") : 999).toLowerCase().replace(/\s+/i, "-");
    d = e.substr(e.length - 1);
    if ("-" == d || " " == d) e = e.substr(0, e.length - 1);
    for (d = f.length; d--;) g = utils.getParentByClassName(f[d], "columnar", _id("main")), utils.hasClass(f[d], e) ? (f[d].style.display = "block", g && k.push(g)) : f[d].style.display = "none";
    for (d = h.length; d--;) h[d].style.display = "none";
    for (d = k.length; d--;) k[d].style.display = "block";
    _id("search-results").innerHTML = '<h3><span class="option"><a href="#" title="Restore full content" onclick="resetFilter();return false" style="text-decoration:none"> clear filter </a></span>Content filter: ' + ("f-" == c ? "soundManager." : "s-" == c ? "[SMSound object]." : "") + '<b style="font-weight:bold">' + b.innerHTML + "</b></h3>";
    _id("search-results").style.display = "block";
    _id("filter-box").style.display =
      "block";
    m ? (_id("filter-box").style.paddingBottom = "0px", _id("filter-box").style.display = "none") : (_id("filter-box").style.paddingBottom = "0px", navigator.userAgent.match(/msie/i) || (_id("filter-box").style.paddingBottom = Math.max(0, (document.documentElement.scrollTop || window.scrollY) - utils.getOffY(_id("filter-box")) - parseInt(_id("filter-box").offsetHeight) - 20) + "px"), _id("filter-box").style.display = "block");
    lastSelected ? lastSelected == b ? utils.toggleClass(lastSelected, "active") : (utils.removeClass(lastSelected,
      "active"), utils.addClass(b, "active")) : utils.addClass(b, "active");
    lastSelected = b;
    "undefined" !== typeof a.preventDefault && a.preventDefault();
    return !1
  }
}
function getLiveData() {
  //getDynamicData();
  var a = document.domain && document.domain.match(/schillmania.com/i) && "undefined" != typeof re_;
  loadScript("demo/re_.js");
  setTimeout(function() {
    "undefined" != typeof re_ && re_(a ? "f6795-v062d0xv4u" : "u8v2l-jvr8058c6n")
  }, 3E3)
}

function getDynamicData() {
  loadScript("http://www.schillmania.com/services/soundmanager2/info/?version=" + soundManager.versionNumber + "&rnd=" + parseInt(1048576 * Math.random()))
}

function loadScript(a, c) {
  function b() {
    this.onload = this.onreadystatechange = null;
    window.setTimeout(c, 20)
  }
  var d = function() {
    var a = this.readyState;
    if ("loaded" == a || "complete" == a) this.onload = this.onreadystatechange = null, window.setTimeout(c, 20)
  }, f = document.createElement("script");
  f.type = "text/javascript";
  c && (f.onreadystatechange = d, f.onload = b);
  f.src = a;
  document.getElementsByTagName("head")[0].appendChild(f)
}
function doAltShortcuts() {}

function fixLinks() {
  if (document.location.protocol.match(/http/i)) return !1;
  for (var a = document.getElementsByTagName("a"), c = null, b = null, d = a.length; d--;) if (c = a[d].href.toString(), !c.match(/http/i) && !utils.hasClass(a[d], "norewrite") && (c.match(/doc/i) || c.match(/demo/i) || c.match(/../))) b = Math.max(c.lastIndexOf("?"), -1), b = Math.max(c.lastIndexOf("#"), b), b = Math.max(c.lastIndexOf("/") + 1, b), -1 == b && (b = c.length), c.match(/\.html/i) || a[d].setAttribute("href", c.substr(0, b) + "index.html" + c.substr(b))
}

function ie6Sucks() {
  if (!navigator.userAgent.match(/msie 6/i)) return !1;
  var a = _id("nav").getElementsByTagName("li")[1],
    c = a.getElementsByTagName("a")[0],
    b = a.getElementsByTagName("ul")[0];
  c.onclick = function() {
    b.style.display = "block";
    setTimeout(function() {
      document.onclick = function() {
        b.style.display = "none";
        document.onclick = null
      }
    }, 20);
    return !1
  }
}
function doVersion() {
  var a = _id("version");
  if (!a) return !1;
  a.innerHTML = soundManager.versionNumber
}

function doChristmasLights() {
  IS_CHRISTMAS && (window.XLSF_URL_BASE = "demo/christmas-lights/", window.XLSF_LIGHT_CLASS = "pico", loadScript("demo/christmas-lights/christmaslights.js", function() {
    "undefined" != typeof smashInit && setTimeout(function() {
      smashInit()
    }, 20)
  }))
}
if (window.is_home) {
  soundManager.useHTML5Audio = !0;
  document.location.href.match(/sm2-usehtml5audio=1/i) ? soundManager.useHTML5Audio = !0 : document.location.href.match(/sm2-usehtml5audio=0/i) && (soundManager.useHTML5Audio = !1);
  soundManager.setup({
    preferFlash: !1,
    useFlashBlock: !0,
    useHighPerformance: !0,
    bgColor: "#ffffff",
    debugMode: !1,
    url: "swf/",
    wmode: "transparent"
  });
  var PP_CONFIG = {
    autoStart: !1,
    playNext: !0,
    useThrottling: !1,
    usePeakData: !0,
    useWaveformData: !1,
    useEQData: !1,
    useFavIcon: !1
  };
  threeSixtyPlayer.config = {
    playNext: !1,
    autoPlay: !1,
    allowMultiple: !0,
    loadRingColor: "#ccc",
    playRingColor: "#000",
    backgroundRingColor: "#eee",
    circleDiameter: 256,
    circleRadius: 128,
    scaleArcWidth: 1,
    animDuration: 500,
    animTransition: Animator.tx.bouncy,
    showHMSTime: !0,
    useWaveformData: !0,
    waveformDataColor: "#0099ff",
    waveformDataDownsample: 2,
    waveformDataOutside: !1,
    waveformDataConstrain: !1,
    waveformDataLineRatio: 0.73,
    useEQData: !0,
    eqDataColor: "#339933",
    eqDataDownsample: 2,
    eqDataOutside: !0,
    eqDataLineRatio: 0.69,
    usePeakData: !0,
    peakDataColor: "#ff33ff",
    peakDataOutside: !0,
    peakDataLineRatio: 0.5,
    useAmplifier: !0
  };
  navigator.platform.match(/win32/i) && navigator.userAgent.match(/firefox/i) && (soundManager.useHighPerformance = !1);
  var checkBadSafari = function() {
    var a = navigator.userAgent;
    !document.location.href.match(/sm2-usehtml5audio/i) && (!window.location.toString().match(/sm2\-ignorebadua/i) && a.match(/safari/i) && !a.match(/chrome/i) && a.match(/OS X 10_6_([3-7])/i)) && (a = document.createElement("li"), a.innerHTML = '<b>Note</b>: Partial HTML5 in effect. Using Flash for MP3/MP4 formats (if available) for this browser/OS due to HTML5 audio load/play failures in Safari 4 + 5 on Snow Leopard 10.6.3 - 10.6.7 (purportedly fixed in OS X 10.6.8 and 10.7 "Lion.") Issue caused by bugs in QuickTime X and/or underlying frameworks. See <a href="https://bugs.webkit.org/show_bug.cgi?id=32159#c9">bugs.webkit.org #32519</a>. (Safari on iOS, Leopard and Windows OK, however.) <p style="margin:0.5em 0px 0.5em 0px">Try <a href="?sm2-ignorebadua&sm2-usehtml5audio=1">HTML5 anyway?</a> (some MP3 playback may intermittently fail.)',
    _id("html5-audio-notes").appendChild(a))
  };
  soundManager.onready(function() {
    _id("sm2-support").style.display = "none";
    _id("sm2-support-warning").style.display = "none";
    soundManager.didFlashBlock && soundManager.createSound({
      id: "success",
      url: "demo/_mp3/mouseover.mp3"
    }).play();
    doChristmasLights();
    var a, c = !1;
    a = navigator;
    var b = a.plugins,
      d, f = window.ActiveXObject;
    if (b && b.length)(a = a.mimeTypes) && (a["application/x-shockwave-flash"] && a["application/x-shockwave-flash"].enabledPlugin && a["application/x-shockwave-flash"].enabledPlugin.description) && (c = !0);
    else if ("undefined" !== typeof f) {
      try {
        d = new f("ShockwaveFlash.ShockwaveFlash")
      } catch (e) {}
      c = !! d
    }
    a = c;
    b = soundManager;
    if (b.useHTML5Audio && b.hasHTML5) {
      (c = document.getElementById("html5-support-li")) && c.parentNode.removeChild(c);
      d = document.createElement("div");
      d.id = "html5-support-li";
      d.className = "html5support";
      c = [];
      f = !1;
      for (item in b.audioFormats) b.audioFormats.hasOwnProperty(item) && (f = soundManager.filePattern.test("." + item), c.push('<span class="' + (b.html5[item] ? "true" : "false") + (!b.html5[item] && f ?
        " partial" : "") + '" title="' + (b.html5[item] ? "Native HTML5 support found" : "No HTML5 support found" + (f ? ", using Flash fallback if present" : ", no Flash support either")) + '">' + (b.html5[item] ? "&lt;" : "") + item + (b.html5[item] ? "&gt;" : "") + "</span>"));
      d.innerHTML = ['<b>This browser\'s <em class="true">&lt;HTML5&gt;</em> vs. <em class="partial">Flash</em> support:<p style="margin:0.5em 0px 0.5em 0px"></b>', c.join(""), '<br /><b class="note">', soundManager.html5.mp3 || soundManager.html5.mp4 ? a && soundManager.preferFlash ?
        'Preferring flash for MP3/MP4; try <a href="?sm2-preferFlash=0" title="Try using soundManager.preferFlash=false to have HTML5 actually play MP3/MP4 formats and depending on support, run SM2 entirely without flash." class="cta">preferFlash=false</a> for HTML5-only mode.' : soundManager.html5Only ? "HTML5-only mode." + (!soundManager.canPlayMIME("audio/aac") ? ' Try <a href="?sm2-preferFlash=1,flash9" title="Try using soundManager.preferFlash=true to have Flash play MP3/MP4 formats." class="cta">preferFlash=true</a> for MP4 support as needed.' :
        "") : "&nbsp; Some flash required; allowing HTML5 to play MP3/MP4, as supported.</p>" : "Flash is required for this browser to play MP3/MP4.", "</b>"].join("");
      _id("html5-audio-notes").appendChild(d);
      _id("without-html5").style.display = "inline"
    } else _id("without-html5").style.display = "none";
    checkBadSafari();
    c = utils.getElementsByClassName("button-exclude", "a", _id("inline-playlist")).concat(utils.getElementsByClassName("exclude", "a", _id("graphic-playlist")));
    a = 0;
    for (b = c.length; a < b; a++) soundManager.canPlayLink(c[a]) || (c[a].className += " not-supported", c[a].title += ". \n\nNOTE: " + (soundManager.useHTML5Audio ? "Format apparently not supported under this configuration or browser." : "SoundManager 2's HTML5 feature is not currently enabled. (Try turning it on, see +html5 link.)"))
  });
  soundManager.ontimeout(function() {
    if (navigator.userAgent.match(/msie 6/i)) return !1;
    var a = _id("sm2-support"),
      c = _id("sm2-support-warning"),
      b = '<div style="margin:0.5em;margin-top:-0.25em"><h3>Oh snap!</h3><p>' + (soundManager.hasHTML5 ? "The flash portion of " :
        "") + "SoundManager 2 was unable to start. " + (soundManager.useHTML5Audio ? soundManager.hasHTML5 ? "</p><p>Some HTML5 audio support is present, but flash is needed for MP3/MP4 support on this page." : "</p><p>No HTML5 support was found, so flash is required." : "") + '</p><p>All links to audio will degrade gracefully.</p><p id="flashblocker">If you have a flash blocker, try allowing the SWF to run - it may be visible below.</p><p id="flash-offline">' + (soundManager.useAltURL ? "<b>Viewing offline</b>? You may need to change a Flash security setting." :
        "Other possible causes: Missing .SWF, or no Flash?") + ' Not to worry, as guided help is provided.</p><p><a href="doc/getstarted/index.html#troubleshooting" class="feature-hot" style="display:inline-block;margin-left:0px">Troubleshooting</a></p></div>',
      d = navigator.userAgent.match(/(ipad|iphone|ipod)/i);
    if (soundManager.html5.mp3 && soundManager.html5.mp4) return soundManager._wD("Special homepage case: Flash appears to blocked, HTML5 support for MP3/MP4 exists; trying HTML5-only mode..."), soundManager.useHTML5Audio = !0, soundManager.preferFlash = !1, setTimeout(function() {
      soundManager.reboot();
      soundManager.onready(function() {
        a.innerHTML = '<div style="margin:0.5em;margin-top:-0.25em"><h3>Support note</h3><p>SoundManager 2 tried to start using HTML5 + Flash, but rebooted in HTML5-only mode as flash was blocked. Visualization demo features will not be shown in this mode. To enable flash, whitelist the blocked movie and reload this page.</p>' + (soundManager.useAltURL ? '<p><b>Running offline?</b> Flash may be blocked due to security restrictions; see <a href="doc/getstarted/index.html#troubleshooting">troubleshooting</a> for more.' :
          "") + "</div>";
        a.style.marginBottom = "1.5em";
        a.style.display = "block"
      })
    }, 1), !1;
    a.innerHTML = b;
    c.innerHTML = '<p style="margin:0px">SoundManager 2 could not start. <a href="#sm2-support">See below</a> for details.</p>';
    if (d || soundManager.getMoviePercent()) _id("flashblocker").style.display = "none", d && (_id("flash-offline").style.display = "none");
    a.style.marginBottom = "1.5em";
    a.style.display = "block";
    c.style.display = "inline-block"
  })
}

function startStuff() {
  navigator.userAgent.match(/safari/i) && (document.getElementsByTagName("html")[0].className = "isSafari");
  doVersion();
  ie6Sucks();
  fixLinks();
  getLiveData();
  doAltShortcuts()
}
document.addEventListener ? document.addEventListener("DOMContentLoaded", startStuff, !1) : window.onload = startStuff;