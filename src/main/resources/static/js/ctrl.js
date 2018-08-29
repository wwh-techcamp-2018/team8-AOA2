class Ctrls {

    constructor(wrapper) {
        this.wrapper = wrapper;
        this.limit = { globalMinValue: 1, globalMaxValue: 9999999999 };
    }
    decrement() {
        this.changeValue(Number(this.targetInput.value) - 1);
    };

    increment() {
        this.changeValue(Number(this.targetInput.value) + 1);
    };

    initClass(eventTarget) {
        this.targetInput = $('.ctrl__counter-num', eventTarget.closest('.ctrl'));
        this.limit.minValue =  Number( this.targetInput.getAttribute('data-min-value') || this.limit.globalMinValue );
        this.limit.maxValue =  Number( this.targetInput.getAttribute('data-max-value') || this.limit.globalMaxValue );
        this.relatedElem = null;
        if(this.targetInput.getAttribute('data-limit-target')){
            this.relatedElem = $(this.targetInput.getAttribute('data-limit-target'));
        }
    };

    updatePrevValue(value) {
        this.targetInput.setAttribute('data-prev', value);
    };
    backToPrevValue(invalidValue) {
        if (this.targetInput.value == invalidValue)
            this.targetInput.value = Number( this.targetInput.getAttribute('data-prev'));
    };

    handleClickEvent(event) {
        if (hasClass(event.target, 'ctrl__button--increment')) {
            this.initClass(event.target);
            this.increment();
            return;
        }
        if (hasClass(event.target, 'ctrl__button--decrement')) {
            this.initClass(event.target);
            this.decrement();
            return;
        }
    };
    changeValue(nextNumber) {
        if (!this.validNextNumber(nextNumber)) {
            this.backToPrevValue(nextNumber);
            return;
        }
        this.targetInput.value = nextNumber;
        this.updatePrevValue(nextNumber);

        if(this.relatedElem)
            this.limitMaxValue(this.relatedElem, nextNumber);

    }
    limitMaxValue(target,newMaxLimit){
        if( target.value > newMaxLimit){
            target.value = newMaxLimit;
        }
        target.setAttribute('data-prev', newMaxLimit);
        target.setAttribute('data-max-value', newMaxLimit);

    }
    validNextNumber(nextNumber) {
        return !isNaN(nextNumber)
            && nextNumber <= this.limit.maxValue
            && nextNumber >= this.limit.minValue;
    }
    handleChangeEvent(event) {
        if (hasClass(event.target, 'ctrl__counter-num')) {
            this.initClass(event.target);
            var parseValue = parseInt(event.target.value);
            this.changeValue(parseValue);
        }
    }
    registerEvent() {
        this.wrapper.addEventListener('click', this.handleClickEvent.bind(this));
        this.wrapper.addEventListener('change', this.handleChangeEvent.bind(this));
    }
}

export default Ctrls;