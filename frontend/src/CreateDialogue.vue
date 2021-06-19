<script>
  export default {
    name: 'createmodal',
    data: function(){
    return{errors: [],
    url: null,
    name: null,
    caption: null,
    postResponse : null
    }
  },
  props: ['decision'],
    methods: {
      close() {
        this.$emit('close');
      },
      checkForm: function () {
      if (this.url != null && this.name != null && this.caption != null)  {
        this.$emit('my-event',this.url,this.name,this.caption); 
      }
       
        //this.$parent.submitMeme();

      this.errors = [];

      if (this.url==null || (this.url!=null && this.url.lenth>100)) {
        this.errors.push('Please give the URL less than 1000 char');
      }
      if (this.name == null) {
        this.errors.push('Author Name is mandatory and should be less than 45 char');
      }

      if (this.caption == null) {
        this.errors.push('Caption is mandatory and should be less than 90 char');
      }

      //e.preventDefault();
    }
    },
  };
</script>
<template>
  <transition name="createmodal-fade">
    <div class="createmodal-backdrop">
      <div class="createmodal"
        role="dialog"
        aria-labelledby="createmodalTitle"
        aria-describedby="createmodalDescription"
      >
        <header
          class="createmodal-header"
          id="createmodalTitle"
        >
          <slot name="createheader">
            Submit Meme

            <button
              type="button"
              class="create-btn-close"
              @click="close"
              aria-label="Close createmodal"
            >
              x
            </button>
          </slot>
        </header>
         <p v-if="errors.length">
    <b>Please correct the following error(s):</b>
    <ul>
      <li v-for="(error, index) in errors" :key="index">{{ error }}</li>
    </ul>
  </p>
        <section
          class="createmodal-body"
          id="createmodalDescription"
        >
          <slot name="createbody">
            <input v-model="url" placeholder="URL with jpg,jpeg and png allowed" id="create-meme-url">
          </slot>
        </section>

<section
          class="createmodal-body"
          id="createmodalAuthor"
        >
          <slot name="createbody">
            <input v-model="name" placeholder="Author Name" id="create-meme-author">
          </slot>
        </section>

        <section
          class="createmodal-body"
          id="createmodalCaption"
        >
          <slot name="createbody">
            <input v-model="caption" placeholder="Caption" id="create-meme-caption">
          </slot>
        </section>

        <footer class="createmodal-footer">
          <slot name="createfooter">
            <button
              type="button"
              class="create-btn-close"
              @click="checkForm"
              aria-label="Close createmodal"
            >
              Submit
            </button>

          </slot>
        </footer>
      </div>
    </div>
  </transition>
</template>
<style>
  .createmodal-backdrop {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background-color: rgba(0, 0, 0, 0.3);
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .createmodal {
    background: #FFFFFF;
    box-shadow: 2px 2px 20px 1px;
    overflow-x: auto;
    display: flex;
    flex-direction: column;
  }

  .createmodal-header,
  .createmodal-footer {
    padding: 15px;
    display: flex;
  }

  .createmodal-header {
    border-bottom: 1px solid #eeeeee;
    color: #4AAE9B;
    justify-content: space-between;
  }

  .createmodal-footer {
    border-top: 1px solid #eeeeee;
    justify-content: flex-end;
  }

  .createmodal-body {
    position: relative;
    padding: 20px 10px;
  }

  .create-btn-close {
    border: none;
    font-size: 20px;
    
    cursor: pointer;
    font-weight: bold;
    color: #4AAE9B;
    background: transparent;
  }

  .btn-green {
    color: white;
    background: #4AAE9B;
    border: 1px solid #4AAE9B;
    border-radius: 2px;
  }
</style>