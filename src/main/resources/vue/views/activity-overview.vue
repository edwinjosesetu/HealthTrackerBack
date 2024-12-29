<template id="activity-overview">
  <app-layout>
    <div v-if="noActivityFound">
      <p> We're sorry, we were not able to retrieve this user.</p>
      <p> View <a :href="'/users'">all users</a>.</p>
    </div>
    <div class="card bg-light mb-3" v-if="!noActivityFound">
      <div class="card-header">
        <div class="row">
          <div class="col-6"> Activity Details </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Update"
                    class="btn btn-info btn-simple btn-link"
                    @click="updateActivity()">
              <i class="far fa-save" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete"
                    class="btn btn-info btn-simple btn-link"
                    @click="deleteActivity()">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>

          </div>
        </div>
      </div>
      <div class="card-body">
        <form>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-id">Activity ID</span>
            </div>
            <input type="number" class="form-control" v-model="activity.id" name="id" readonly placeholder="Id"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-description">description</span>
            </div>
            <input type="text" class="form-control" v-model="activity.description" name="description" placeholder="description"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-duration">duration</span>
            </div>
            <input type="number" class="form-control" v-model="activity.duration" name="duration" placeholder="duration"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-calories">calories</span>
            </div>
            <input type="number" class="form-control" v-model="activity.calories" name="calories" placeholder="calories"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-started">started</span>
            </div>
            <input type="text" class="form-control" v-model="activity.started" name="started" placeholder="started"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-userid">User Id</span>
            </div>
            <input type="number" class="form-control" v-model="activity.userId" name="userid" placeholder="userid"/>
          </div>

        </form>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component("activity-overview", {
  template: "#activity-overview",
  data: () => ({
    user: null,
    noActivityFound: false,
    activity: [],
  }),

  created: function () {
    const activityId = this.$javalin.pathParams["activity-id"];
    const url = `/api/users/${activityId}/get-activities`
    axios.get(url)
        .then(res => this.activity = res.data)
        .catch(error => {
          console.log("No user found for id passed in the path parameter: " + error)
          this.noActivityFound = true
        })
  },
  methods: {
    updateActivity: function () {
      const activityId = this.$javalin.pathParams["activity-id"];
      const url = `/api/users/${activityId}/update-activity`
      axios.patch(url,
          {
            description: this.activity.description,
            duration: this.activity.duration,
            calories: this.activity.calories,
            started: this.activity.started,
            userId: this.activity.userId

          })
          .then(response => {
            window.location.href ='/activities';
          })
          .catch(error => {
            console.log(error)
          })
      alert("activity updated!")
    },
    deleteActivity: function () {
      if (confirm("Do you really want to delete?")) {
        const activityId = this.$javalin.pathParams["activity-id"];
        const url = `/api/users/${activityId}/remove-activity`
        axios.delete(url)
            .then(response => {
              alert("Activity deleted")
              window.location.href = '/activities';
            })
            .catch(function (error) {
              console.log(error)
            });
      }
    }
  }
});
</script>