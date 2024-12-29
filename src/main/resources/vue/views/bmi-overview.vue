<template id="bmi-overview">
  <app-layout>
    <div v-if="noBmiFound">
      <p> We're sorry, we were not able to retrieve this bmi.</p>
      <p> View <a :href="'/bmies'">all users</a>.</p>
    </div>
    <div class="card bg-light mb-3" v-if="!noBmiFound">
      <div class="card-header">
        <div class="row">
          <div class="col-6"> Bmi Details </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Update"
                    class="btn btn-info btn-simple btn-link"
                    @click="updateBmi()">
              <i class="far fa-save" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete"
                    class="btn btn-info btn-simple btn-link"
                    @click="deleteBmi()">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>

          </div>
        </div>
      </div>
      <div class="card-body">
        <form>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-bmi-id">Bmi ID</span>
            </div>
            <input type="number" class="form-control" v-model="bmi.id" name="id" readonly placeholder="Id"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-bmi-weight">Weight</span>
            </div>
            <input type="number" class="form-control" v-model="bmi.weight" name="weight" placeholder="weight"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-bmi-duration">Height</span>
            </div>
            <input type="number" class="form-control" v-model="bmi.height" name="height" placeholder="height"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-bmi-calculation">BMI</span>
            </div>
            <input type="number" class="form-control" v-model="bmi.bmiCalculation" name="bmiCalculation" placeholder="bmi"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-bmi-timestamp">Date</span>
            </div>
            <input type="text" class="form-control" v-model="bmi.timestamp" name="timestamp" placeholder="date"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-bmi-userid">User Id</span>
            </div>
            <input type="number" class="form-control" v-model="bmi.userId" name="userid" placeholder="userid"/>
          </div>

        </form>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component("bmi-overview", {
  template: "#bmi-overview",
  data: () => ({
    user: null,
    noBmiFound: false,
    bmi: [],
  }),

  created: function () {
    const bmisId = this.$javalin.pathParams["bmis-id"];
    const url = `/api/bmi/${bmisId}`
    axios.get(url)
        .then(res => this.bmi = res.data)
        .catch(error => {
          console.log("No user found for id passed in the path parameter: " + error)
          this.noBmiFound = true
        })
  },
  methods: {
    updateBmi: function () {
      const bmisId = this.$javalin.pathParams["bmis-id"];
      const url = `/api/bmi/${bmisId}`
      axios.patch(url,
          {
            weight: this.bmi.weight,
            height: this.bmi.height,
            bmiCalculation: this.bmi.bmiCalculation,
            timestamp: this.bmi.timestamp,
            userId: this.bmi.userId

          })
          .then(response => {
            window.location.href ='/bmies';
          })
          .catch(error => {
            console.log(error)
          })
      alert("bmi updated!")
    },
    deleteBmi: function () {
      if (confirm("Do you really want to delete?")) {
        const bmisId = this.$javalin.pathParams["bmis-id"];
        const url = `/api/bmi/delete-bmiId/${bmisId}`
        axios.delete(url)
            .then(response => {
              alert("Bmi deleted")
              window.location.href = '/bmies';
            })
            .catch(function (error) {
              console.log(error)
            });
      }
    }
  }
});
</script>