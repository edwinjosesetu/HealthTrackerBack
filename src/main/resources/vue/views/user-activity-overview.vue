<template id="user-activity-overview">
  <app-layout>
    <div v-if="noActivitiesFound">
      <p>No activities found for this user.</p>
      <p> View <a :href="'/users'">all users</a>.</p>
    </div>
    <div class="card bg-light mb-3" v-if="!noActivitiesFound">
      <div class="card-header">
        <div class="row">
          <div class="col-6"> User Activities </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Add Activity"
                    class="btn btn-info btn-simple btn-link"
                    @click="hideForm =!hideForm">
              <i class="fas fa-plus" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Refresh Activities"
                    class="btn btn-success btn-simple btn-link"
                    @click="fetchActivities()">
              <i class="fas fa-sync" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
      <div class="card-body" :class="{ 'd-none': hideForm}">
        <form>
<!--          <div class="input-group mb-3">-->
<!--            <div class="input-group-prepend">-->
<!--              <span class="input-group-text" id="input-activity-id">ID</span>-->
<!--            </div>-->
<!--            <input type="number" class="form-control" v-model="activities.id" name="id" placeholder="Id"/>-->
<!--          </div>-->
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-description">Description</span>
            </div>
            <input type="text" class="form-control" v-model="formData.description" name="description" placeholder="Description"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-calories">Calories</span>
            </div>
            <input type="number" class="form-control" v-model="formData.calories" name="calories" placeholder="Calories"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-calories">Duration</span>
            </div>
            <input type="number" class="form-control" v-model="formData.duration" name="duration" placeholder="Duration"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-calories">Date</span>
            </div>
            <input type="datetime-local" class="form-control" v-model="formData.started" name="date" placeholder="Date"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-id">User ID</span>
            </div>
            <input type="number" class="form-control" v-model="formData.userId" name="id"  placeholder="Id"/>
          </div>
        </form>
        <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addActivity()">Submit</button>
      </div>
      <div class="card-body">
        <ul>
          <li v-for="activity in activities" :key="activity.id">
            {{ activity.description }} for {{ activity.duration }} minutes, by {{ getUserName(activity.userId) }}
            <a :href="`/activities/${activity.id}`">
            <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="updateActivity(activity.id)">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </button>
            </a>
            <button rel="tooltip" title="Delete"
                    class="btn btn-info btn-simple btn-link" @click="deleteActivity(activity.id)">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </li>
        </ul>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component("user-activity-overview", {
  template: "#user-activity-overview",
  data: () => ({
    activities: [],
    noActivitiesFound: false,
    formData: [],
    users: {},
    hideForm :true,
  }),
  created: function () {
    this.fetchActivities();
    this.fetchUsers();
  },
  methods: {
    fetchActivities: function () {
      axios.get(`/api/activities`)
          .then(res => this.activities = res.data)
          .catch(() => alert("Error while fetching activity"));
    },
    fetchUsers: function () {
      axios.get("/api/users")
          .then(res => {
            this.users = res.data.reduce((map, user) => {
              map[user.id] = user.name; // Map userId to userName
              return map;
            }, {});
          })
          .catch(() => alert("Error while fetching users"));
    },
    getUserName: function (userId) {
      return this.users[userId] || "Unknown User"; // if user not found
    },
    deleteActivity: function (activityid){
      if (confirm("Do you want to delete this activity?")){
        const url = `/api/users/${activityid}/remove-activity`
        axios.delete(url)
            .then(response =>{
              alert("Activity Deleted")
              window.location.href = '/activities'
            })
            .catch(function (error){
              console.log(error)
            });
      }
    },
    addActivity: function (){
      const url = '/api/add-activities';
      axios.post(url,
          {
            description: this.formData.description,
            duration: this.formData.duration,
            calories: this.formData.calories,
            started: this.formData.started,
            userId: this.formData.userId
          })
          .then(response => {
            this.hideForm = true;
            this.formData = {};
            this.fetchActivities();
          })
          .catch(error => {
            console.log(error)
          })
    }
  },
});
</script>