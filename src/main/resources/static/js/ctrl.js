class Ctrls {

    constructor(wrapper){
        this.wrapper = wrapper;
    }
    decrement() {
      let counter = this.targetInput.value;
      const nextCounter = (this.counter > 0) ? --counter : counter;
      this.targetInput.value = nextCounter;
      this.updatePrevValue(nextCounter);
    };

    increment() {
      let counter = this.targetInput.value;
      const nextCounter = (counter < 9999999999) ? ++counter : counter;
      this.targetInput.value = nextCounter;
      this.updatePrevValue(nextCounter);
    };

    initClass(eventTarget){
        this.targetInput = $('.ctrl__counter-num', eventTarget.closest('.ctrl'));
        this.counter = Number(this.targetInput.value);
    };

    updatePrevValue(value){
        this.targetInput.setAttribute('data-prev', value);
    };
    backToPrevValue(){
        this.targetInput.value = this.targetInput.getAttribute('data-prev');
    };

    handleClickEvent(event) {
            if(hasClass(event.target, 'ctrl__button--increment')){
             console.log('up');
             this.initClass(event.target);
             this.increment();
             return;
            }
             if(hasClass(event.target, 'ctrl__button--decrement')){
                console.log('down');
                 this.initClass(event.target);
                 this.decrement();
                return;
         }
    };
    handleChangeEvent(event){
            if(hasClass(event.target, 'ctrl__counter-num')){
             console.log('change');
              this.initClass(event.target);
             var parseValue = parseInt(event.target.value);
                if (isNaN(parseValue) || parseValue < 0) {
                    this.backToPrevValue();
                    return;
                }
                this.targetInput.value = parseValue;
                this.updatePrevValue(parseValue);

            }
    }
    registerEvent() {
        this.wrapper.addEventListener('click', this.handleClickEvent.bind(this));
        this.wrapper.addEventListener('change', this.handleChangeEvent.bind(this));
    }
}

export default Ctrls;