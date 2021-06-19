<template>
  <div id="app">
    
    

<div v-if="displayMemes" class="masterdiv">


   
    </div>


  <h1>Latest Memes</h1>
      <button class="btn" @click="openCreateMeme">CreateDialogue</button>
  <CreateDialogue
      v-show="isCreateMemeVisible"
      v-on:my-event="doSomething"
      @close="closeCreateMeme"
    />

  <div class="memes-contain">
    <div class="locations" v-for="singleMeme in memesJSON" :key="singleMeme.id" :id="singleMeme.id">
       <div class="single-meme">
         <div>
      <img :src="singleMeme.url" width="300" height="300"/>
      <slot></slot>
        <h2>Submitted by : {{ singleMeme.name }}</h2>
      <p>Caption : {{ singleMeme.caption }}</p>
      <i>Posted at: {{convertToDate(singleMeme.created_time)}}</i>
      </div>
       </div>
    </div>
  </div>
  

</div>
  

  
</template>



<script>
import connections from './js/conection'
    import ModalDirection from "./Dialogue";
    import CreateDialogue from "./CreateDialogue";
    import moment from 'moment'

export default {
  name: 'app',
   components: {
    ModalDirection,CreateDialogue 
  },
  data () {
    return {
      displayMemes :false,
      memesJSON:[],
      memesResponse:[],
      msg: 'Welcome to XMEME',
      modalOpen: false,
      isModalVisible: false,
      isCreateMemeVisible : false,
      currentMeme:null,
      authorid:null,
    }
  },
  methods: {
      bodyClick: async function () {
        this.memesResponse = (await connections.axiosGet(''))
        if(this.memesResponse.status ==200){
          this.memesJSON = this.memesResponse.data;
        }
        
        this.displayMemes = true;
        console.log(this.memesResponse.status)
      },
      convertToDate: function (date) {
        return moment(parseInt(date)).format('DD MMM YYYY')	// No I18N
        },
       openModal() {
            this.modalOpen = !this.modalOpen;
        } ,
        showModal() {
        this.isModalVisible = true;
      },
      closeModal() {
        this.isModalVisible = false;
      },

      openCreateMeme(){
        this.isCreateMemeVisible = true
      },
      closeCreateMeme(){
        this.isCreateMemeVisible = false
      },
      doSomething: async function(url,name,caption){
        var jsonToSend = {};
          jsonToSend['url'] = url;
          jsonToSend['name'] = name;
          jsonToSend['caption'] = caption;

          var postResponse = (await connections.axiosPost('',jsonToSend))
          console.log(postResponse);
          if (postResponse.id) {
            this.isCreateMemeVisible = false;
                    location.reload();
          } else if(postResponse.status == 409){
            this.isCreateMemeVisible = false;
            this.url = null;
            alert('You have submitted same URL and caption');
          } else if(postResponse.status == 404){
            this.isCreateMemeVisible = false;
            this.url = null;
            alert(postResponse.data.message);
          }
      },
      swapComponent :function(memeToShow){

        console.log(memeToShow)
       this.currentMeme =memeToShow; 
       this.isModalVisible = true;
      }
  },
  async mounted () {
		this.bodyClick()
	}
  
}
</script>

<style>
.single-meme {
  display: flex;
  flex-direction: column;
  justify-content: center;
  background: white;
  border: 1px solid #ddd;
  padding: 20px 20px;
  margin: 10px;
  width: 25%;
    list-style: none;
    height: 450px;
    max-height: 500px;
    float: left;
}

.memes-contain {
  justify-content: wrap;
  padding-left: 0;
  max-width: inherit;
}

</style>
