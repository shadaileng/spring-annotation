$(function () {
    if($("[data-component-upload='true']").length) $("[data-component-upload='true']").fileinput({})
    if($('[data-component-markdown="ture"]').length) $('[data-component-markdown="ture"]').markdownEditor({useTwemoji: true})
})