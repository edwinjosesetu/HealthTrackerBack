<!-- the "home-page" element is passed as a parameter to VueComponent in the JavalinConfig file -->
<template id="home-page">
  <app-layout>
    <div class="row">
      <div class="col">
        <div class="card">
          <h5 class="card-header">Registered Users</h5>
          <div class="card-body">
            <h5 class="card-title">{{users.length}} users</h5>
            <a href="/users" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <h5 class="card-header">Total Activities</h5>
          <div class="card-body">
            <h5 class="card-title">{{activities.length}} activities</h5>
            <a href="/activities" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <h5 class="card-header">All Meals</h5>
          <div class="card-body">
            <h5 class="card-title">{{meals.length}} meals</h5>
            <a href="/meals" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col">
        <div class="card">
          <h5 class="card-header">All BMI</h5>
          <div class="card-body">
            <h5 class="card-title">{{bmis.length}} bmis</h5>
            <a href="/bmis" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <h5 class="card-header">All Sleep</h5>
          <div class="card-body">
            <h5 class="card-title">{{sleeps.length}} sleeps</h5>
            <a href="/sleeps" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <h5 class="card-header">Water Intake</h5>
          <div class="card-body">
            <h5 class="card-title">{{waters.length}} waters</h5>
            <a href="/waters" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component('home-page',
    {
      template: "#home-page",
      data: () => ({
        users: [],
        activities: [],
        meals: [],
        bmis: [],
        sleeps: [],
        waters:[]
      }),
      created() {
        axios.get("/api/users")
            .then(res => this.users = res.data)
            .catch(() => alert("Error while fetching users"));
        axios.get("/api/activities")
            .then(res => this.activities = res.data)
            .catch(() => alert("Error while fetching activities"));
        axios.get("/api/meals")
            .then(res => this.meals = res.data)
            .catch(() => alert("Error while fetching meals"));
        axios.get("/api/bmi")
            .then(res => this.bmis = res.data)
            .catch(() => alert("Error while fetching BMIs"));
        axios.get("/api/sleep")
            .then(res => this.sleeps = res.data)
            .catch(() => alert("Error while fetching sleep"));
        axios.get("/api/water")
            .then(res => this.waters = res.data)
            .catch(() => alert("Error while fetching Water"));
      }
    });
</script>