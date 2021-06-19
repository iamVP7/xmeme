<script>
  export default {
    name: 'editmodal',
    data: function(){
    return{errors: [],
    postResponse : null,
    
    }
  },
  props:{
    updateurl: {
      type: String,
    
    default :null
    },
    updatename:{
      type: String,
    
    default :null
    },
    updatecaption:{
       type: String,
    default :null
    }
    
  },
    methods: {
      close() {
        this.$emit('close');
      },
       checkUpdateForm: function () {
      if (this.updateurl != null && this.updatecaption != null)  {
        this.$emit('my-update',this.updateurl,this.updatecaption); 
      }
       
        //this.$parent.submitMeme();

      this.errors = [];

      if (!this.updateurl) {
        this.errors.push('Please give the URL');
      }
      
      if (!this.updatecaption) {
        this.errors.push('Caption is mandatory and should be less than 90 char');
      }

      //e.preventDefault();
    }
    }
  };
</script>
<template>
  <transition name="editmodal-fade">
    <div class="editmodal-backdrop">
      <div class="editmodal"
        role="dialog"
        aria-labelledby="editmodalTitle"
        aria-describedby="editmodalDescription"
      >
        <header
          class="editmodal-header"
          id="editmodalTitle"
        >
          <slot name="header">
            Submitted by: {{updatename}}

            <button
              type="button"
              class="btn-close"
              @click="close"
              aria-label="Close editmodal"
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
          class="editmodal-body"
          id="editmodalDescription"
        >
        <label type=“text”>Enter URL</label>
          <slot name="editbody">
            <input placeholder="Submit meme URL" id="edit-meme-url" v-model="updateurl">
          </slot>
        </section>

<section
          class="editmodal-body"
          id="editmodalCaption"
        >
        <label type=“text”>Enter Caption</label>
          <slot name="editbody">
            <input placeholder="Caption" id="edit-meme-caption" v-model="updatecaption">
          </slot>
        </section>

        <footer class="editmodal-footer">
          <slot name="footer">
            <button
              type="button"
              class="edit-btn-close"
              @click="checkUpdateForm"
              aria-label="Close editmodal"
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
  .editmodal-backdrop {
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

  .editmodal {
    background: #FFFFFF;
    box-shadow: 2px 2px 20px 1px;
    overflow-x: auto;
    display: flex;
    flex-direction: column;
  }

  .editmodal-header,
  .editmodal-footer {
    padding: 15px;
    display: flex;
  }

  .editmodal-header {
    border-bottom: 1px solid #eeeeee;
    color: #4AAE9B;
    justify-content: space-between;
  }

  .editmodal-footer {
    border-top: 1px solid #eeeeee;
    justify-content: flex-end;
  }

  .editmodal-body {
    position: relative;
    padding: 20px 10px;
  }

  .btn-close {
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