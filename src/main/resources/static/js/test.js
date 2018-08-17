document.addEventListener('DOMContentLoaded', () => {
    const counter = $All('.input_counter');
    M.CharacterCounter.init(counter);
});

String.prototype.isEmpty = () => {
    return (this.length === 0 || !this.trim());
};

const validInput = () => {
    if($('.invalid')) {
        $('.invalid').focus();
        return false;
    }
    const emptyValidInputs = Array.from($All('.not-empty'));
    emptyValidInputs.forEach( e => {
        if(e.value === ''){
            e.addClass('invalid');
            e.focus();
            return false;
        }
    });
    return true;

};
