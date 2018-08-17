function $(selector, context = document) {
    return context.querySelector(selector);
}
function $All(selector, context = document) {
    return context.querySelectorAll(selector);
}