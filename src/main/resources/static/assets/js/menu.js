"use strict";
var defpage, rightFrame, lastFun;

function initView() {
    var e = JSON.parse(haoutil.storage.get("user"));
    $("#lblUserName").html(e.name), $("#userInfo").on("mouseenter", function () {
        var e = $(this).find("dl"), t = e.children(), n = t.size();
        e.css({height: $(t.get(n - 1)).height() * n + 6})
    }), $("#userInfo").on("mouseleave", function () {
        $(this).find("dl").css({height: 0})
    }), (rightFrame = document.getElementById("rightFrame")).attachEvent ? rightFrame.attachEvent("onload", rightFrame_onload) : rightFrame.onload = rightFrame_onload, $.ajax({
        url: "data/menu.json",
        type: "get",
        dataType: "json",
        cache: !1,
        beforeSend: function () {
            $("#navLoadEle").show()
        },
        success: function (e) {
            $("#navLoadEle").hide(), defpage = e.defpage, initMenu(e, clkMenuNodeItem)
        },
        error: function (e) {
            $("#navLoadEle").hide()
        }
    })
}

function initMenu(e, l) {
    var t, n, a, i, s, r = e.menu || [], d = $("#menu").get(0), o = $("#navList"), c = $("#positionMarker").get(0),
        h = 0, u = 0, m = "active", f = "px", g = function (e) {
            var t = 0, n = 0, a = e.children, i = 0, l = 0, s = document, r = null, d = s.createElement("dl"), o = null,
                c = null, h = null, u = null, m = null, f = null;
            if (a && $.isArray(a) && 0 < a.length) for (t = 0, n = a.length; t < n; ++t) if ((m = a[t]).children) {
                if (r = s.createElement("dl"), o = s.createElement("dt"), u = s.createElement("span"), h = s.createElement("a"), u.className = m.icon || "", m.uri && ((h = s.createElement("a")).href = "javascript:void(0)", o.appendChild(h)), o.appendChild(u), o.innerHTML += m.name || "", r.appendChild(o), m.children) for (i = 0, l = m.children.length; i < l; ++i) f = m.children[i], c = s.createElement("dd"), (h = s.createElement("a")).href = "javascript:void(0)", (u = s.createElement("span")).className = f.icon || "", h.appendChild(u), h.innerHTML += f.name || "", h.data = f, c.appendChild(h), r.appendChild(c);
                C.appendChild(r)
            } else o = s.createElement("dt"), u = s.createElement("span"), h = s.createElement("a"), u.className = m.icon || "", h.href = "javascript:void(0)", h.appendChild(u), h.innerHTML += m.name || "", h.data = m, d.className = "secList", o.appendChild(h), d.appendChild(o), C.appendChild(d);
            return C
        }, v = function (e) {
            e || window.event, e.target || e.srcElement;
            var t, n = $(this).find(".navCon"), a = $(this).index(), i = $(this).offset().left;
            F = $(document).width(), d.innerHTML = "", n.html(""), n.removeClass(m), $(d).append(g(r[a])), n.append(g(r[a])), 1 < $(d).children().size() ? $(d).css({width: "auto"}) : $(d).children().size(), t = $(d).width(), i = F - t - 30 <= i ? F - t - 30 : i, $(d).css({left: i + f}), $(c).css({
                left: $(this).offset().left + f,
                width: $(this).width()
            }), $("#menu dl").on("click", function (e) {
                e.preventDefault(), "A" === e.target.tagName && $.isFunction(l) && e.target.data && l(e.target.data)
            }), 1 <= $(d).children().size() && $(d).addClass(m), p = a
        }, p = -1, C = document.createDocumentFragment(), w = 768, F = $(document).width(), E = null;
    if ($.isArray(r)) {
        for (h = 0, u = r.length; h < u; ++h) C.appendChild((t = r[h], i = a = n = void 0, n = document.createElement("li"), a = document.createElement("a"), i = document.createElement("span"), s = document.createElement("div"), i.className = t.icon || "", a.href = "javascript:void(0)", a.appendChild(i), a.innerHTML += t.name || "", $(s).addClass("navCon"), t.children || (a.data = t), n.appendChild(a), n.appendChild(s), n));
        o.get(0).appendChild(C), $("#navList li").on("mouseenter", v), $("#navList li").on("click", function (e) {
            $(this);
            v.call(this, e), "A" === e.target.tagName && $.isFunction(l) && e.target.data && l(e.target.data)
        }), $("#navList li").on("mouseleave", function (e) {
            e || L.event;
            var t = $(this).find(".navCon");
            e.target || e.srcElement;
            $(c).css({left: $(this).offset().left, width: 0}), t.removeClass(m), $(d).removeClass(m)
        }), $(d).on("mouseenter", function () {
            var e = o.children().get(p);
            for (h = 0, u = o.children().size(); h < u; ++h) E = o.children().get(h), $(E.children[0]).removeClass(m);
            $(c).css({
                left: $(e).offset().left + f,
                width: $(e).width()
            }), $(o.children().get(p).children[0]).addClass(m), $(this).addClass(m)
        }), $(d).on("mouseleave", function () {
            var e = o.children().get(p).children[0];
            $(d).removeClass(m), $(e).removeClass(m), $(c).css({left: $(e).offset().left + $(e).width(), width: 0})
        }), $(mediaNav).on("click", function () {
            o.hasClass(m) ? (o.find("dl").removeClass(m), o.find(".navCon").removeClass(m), o.removeClass(m), $("#navList li").removeClass(m)) : o.addClass(m)
        }), $(window).on("resize", function () {
            F = $(document).width(), w < F ? ($("#navList li").removeClass(m), $(c).show()) : $(c).hide()
        }), o.find("li").on("click", function (e) {
            var t = $(this), n = (e.target, null);
            F < w && (n = t.find(".navCon"), t.find("dl").on("click", function (e) {
                var t = $(this);
                e.preventDefault(), e.stopPropagation ? e.stopPropagation() : e.cancelBubble = !0, "A" === e.target.tagName && $.isFunction(l) && e.target.data ? l(e.target.data) : t.hasClass(m) ? t.removeClass(m) : t.addClass(m)
            }), t.hasClass(m) ? t.removeClass(m) : ($("#navList li").removeClass(m), 0 < n.children().size() && t.addClass(m)))
        })
    }
}

function clkMenuNodeItem(e) {
    rightFrame.contentWindow.isautopaly = false;
    lastFun = null;
    var t = e.uri;
    t && 0 < t.length ? t.endsWith("js") || -1 != t.indexOf(".js?") ? showPage(defpage) ? lastFun = function () {
        rightFrame.contentWindow.activateWidget(e)
    } : rightFrame.contentWindow.activateWidget(e) : showPage(t) : showPage(defpage);
    var n = e.calback;
    n && 0 < n.length && (rightFrame.contentWindow && rightFrame.contentWindow.activateFunByMenu ? rightFrame.contentWindow.activateFunByMenu(n) : lastFun = function () {
        rightFrame.contentWindow.activateFunByMenu(n)
    })
}

function rightFrame_onload() {
    lastFun && setTimeout(function () {
        lastFun()
    }, 200)
}

function showPage(e) {
    return null != e && "" != e && ($("#rightFrame").attr("src") != e && ($("#rightFrame").attr("src", e), !0))
}

function logout() {
    sendAjax({
        url: "user/logout", type: "post", dataType: "json", success: function (e) {
        }
    }), location.href = "login.html"
}

$(document).ready(function () {
    initView()
});