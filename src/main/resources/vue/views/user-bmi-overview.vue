<template id="user-bmi-overview">
  <app-layout>
    <div v-if="noBmisFound">
      <p>No bmis found for this user.</p>
      <p> View <a :href="'/users'">all user bmi</a>.</p>
    </div>
    <div class="card bg-light mb-3" v-if="!noBmisFound">
      <div class="card-header">
        <div class="row">
          <div class="col-6"> User Bmis </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Add Bmi"
                    class="btn btn-info btn-simple btn-link"
                    @click="hideForm =!hideForm">
              <i class="fas fa-plus" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Refresh Bmis"
                    class="btn btn-success btn-simple btn-link"
                    @click="fetchBmis()">
              <i class="fas fa-sync" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
      <div class="card-body" :class="{ 'd-none': hideForm}">
        <form>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-bmi-weight">Weight</span>
            </div>
            <input type="text" class="form-control" v-model="formData.weight" name="weight" placeholder="Weight"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-bmi-height">Height</span>
            </div>
            <input type="number" class="form-control" v-model="formData.height" name="height" placeholder="Height"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-bmi-timestamp">Date</span>
            </div>
            <input type="datetime-local" class="form-control" v-model="formData.timestamp" name="timestamp" placeholder="Date"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-id">User ID</span>
            </div>
            <input type="number" class="form-control" v-model="formData.userId" name="id"  placeholder="Id"/>
          </div>
        </form>
        <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addBmi()">Submit</button>
      </div>
      <div class="card-body">
        <ul>
          <li v-for="bmi in bmis" :key="bmi.id">
            Weight: {{ bmi.weight }}, Height: {{ bmi.height }}, BMI: {{ bmi.bmiCalculation }}, ({{ getUserName(bmi.userId) }})
            <a :href="`/bmis/${bmi.id}`">
              <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="updateBmi(bmi.id)">
                <i class="fa fa-pencil" aria-hidden="true"></i>
              </button>
            </a>
            <button rel="tooltip" title="Delete"
                    class="btn btn-info btn-simple btn-link" @click="deleteBmi(bmi.id)">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </li>
        </ul>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component("user-bmi-overview", {
  template: "#user-bmi-overview",
  data: () => ({
    bmis: [],
    noBmisFound: false,
    formData: [],
    users: {},
    hideForm :true,
  }),
  created: function () {
    this.fetchBmis();
    this.fetchUsers();
  },
  methods: {
    fetchBmis: function () {
      axios.get(`/api/bmi`)
          .then(res => this.bmis = res.data)
          .catch(() => alert("Error while fetching bmi"));
    },
    fetchUsers: function () {
      axios.get("/api/users")
          .then(res => {
            this.users = res.data.reduce((map, user) => {
              map[user.id] = user.name;
              return map;
            }, {});
          })
          .catch(() => alert("Error while fetching users"));
    },
    getUserName: function (userId) {
      return this.users[userId] || "Unknown User";
    },
    deleteBmi: function (bmiId){
      if (confirm("Do you want to delete this bmi?")){
        const url = `/api/bmi/delete-bmiId/${bmiId}`
        axios.delete(url)
            .then(response =>{
              alert("Bmi Deleted")
              window.location.href = '/bmies'
            })
            .catch(function (error){
              console.log(error)
            });
      }
    },
    addBmi: function (){
      const url = '/api/bmi/calculate-bmi';
      axios.post(url,
          {
            weight: this.formData.weight,
            height: this.formData.height,
            timestamp: this.formData.timestamp,
            userId: this.formData.userId
          })
          .then(response => {
            this.hideForm = true;
            this.formData = {};
            this.fetchBmis();
          })
          .catch(error => {
            console.log(error)
          })
    }
  },
});
</script>