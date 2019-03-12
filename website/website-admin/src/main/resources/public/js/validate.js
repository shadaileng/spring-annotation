var Validate = function () {
    var handleInit = function () {
        $.validator.addMethod("mobile", function (value, element) {
            let length = value.length
            let mobile = new RegExp('^(13[0-9]{1})|(15[0-9]{1})\\d{8}')
            return this.optional(element) || (length == 11 && mobile.test(value))
        }, "手机号码格式错误")
    }

    var handleValidate = function (formId) {
        $('#' + formId).validate({
            errorElement: 'span',
            errorClass: 'help-block',
            errorPlacement: function (error, element) {
                element.parent().parent().attr("class", "form-group has-error")
                error.insertAfter(element)
            }
        })
    }

    return {
        init: function () {
            handleInit()
        },
        validateForm: function (formId) {
            handleValidate(formId)
        }
    }
}();

$(function () {
    if(jQuery) {
        console.log("加载validate")
        Validate.init()
    } else {
        console.log("jQuery未加载...")
    }
})