<template id="user-profile">
  <app-layout>
    <div v-if="noUserFound">
      <p> We're sorry, we were not able to retrieve this user.</p>
      <p> View <a :href="'/users'">all users</a>.</p>
    </div>
    <div class="card bg-light mb-3" v-if="!noUserFound">
      <div class="card-header">
        <div class="row">
          <div class="col-6"> User Profile </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Update"
                    class="btn btn-info btn-simple btn-link"
                    @click="updateUser()">
              <i class="far fa-save" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete"
                    class="btn btn-info btn-simple btn-link"
                    @click="deleteUser()">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
      <div class="card-body">
        <form>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-id">User ID</span>
            </div>
            <input type="number" class="form-control" v-model="user.id" name="id" readonly placeholder="Id"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-name">Name</span>
            </div>
            <input type="text" class="form-control" v-model="user.name" name="name" placeholder="Name"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-email">Email</span>
            </div>
            <input type="email" class="form-control" v-model="user.email" name="email" placeholder="Email"/>
          </div>
        </form>
      </div>
      <div class="card-footer text-left">
        <p  v-if="activities.length === 0"> No activities yet...</p>
        <p  v-if="activities.length > 0"> Activities so far...</p>
        <ul>
          <li v-for="activity in activities">
            {{ activity.description }} for {{ activity.duration }} minutes
          </li>
        </ul>
        <p  v-if="bmis.length === 0"> No BMIs yet...</p>
        <p  v-if="bmis.length > 0"> BMIs so far...</p>
        <ul>
          <li v-for="bmi in bmis">
            Height: {{ bmi.height }}, Weight: {{ bmi.weight }}, BMI: {{ bmi.bmiCalculation }}
          </li>
        </ul>
        <p  v-if="sleeps.length === 0"> No Sleep yet...</p>
        <p  v-if="sleeps.length > 0"> Sleeps so far...</p>
        <ul>
          <li v-for="sleep in sleeps">
            Slept for {{ sleep.duration }} hours on {{ sleep.date }},
          </li>
        </ul>
        <p  v-if="waters.length === 0"> No Water consumed  yet...</p>
        <p  v-if="waters.length > 0"> Water Consumption so far...</p>
        <ul>
          <li v-for="water in waters">
            Drank  {{ water.amount }} Litre of water on {{ water.date }},
          </li>
        </ul>
        <p  v-if="meals.length === 0"> No Meal consumed  yet...</p>
        <p  v-if="meals.length > 0"> Meal Consumption so far...</p>
        <ul>
          <li v-for="meal in meals">
            Ate {{ meal.foodItems }} for {{ meal.mealType }} on {{meal.loggedAt}},
          </li>
        </ul>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component("user-profile", {
  template: "#user-profile",
  data: () => ({
    user: null,
    noUserFound: false,
    activities: [],
    bmis: [],
    sleeps:[],
    waters:[],
    meals:[],
  }),
  created: function () {
    const userId = this.$javalin.pathParams["user-id"];
    const url = `/api/users/${userId}`
    axios.get(url)
        .then(res => this.user = res.data)
        .catch(error => {
          console.log("No user found for id passed in the path parameter: " + error)
          this.noUserFound = true
        })
    axios.get(url + `/activities`)
        .then(res => this.activities = res.data)
        .catch(error => {
          console.log("No activities added yet (this is ok): " + error)
        })
    axios.get(`/api/bmi/users/${userId}`)
        .then(res => this.bmis = res.data)
        .catch(error => {
          console.log("No BMI added yet (this is ok): " + error)
        })
    axios.get(`/api/sleep/users/${userId}`)
        .then(res => this.sleeps = res.data)
        .catch(error => {
          console.log("No Sleep added yet (this is ok): " + error)
        })
    axios.get(`/api/water/users/${userId}`)
        .then(res => this.waters = res.data)
        .catch(error => {
          console.log("No water consumption added yet (this is ok): " + error)
        })
    axios.get(`/api/users/meals/${userId}`)
        .then(res => this.meals = res.data)
        .catch(error => {
          console.log("No meal consumption added yet (this is ok): " + error)
        })
  },
  methods: {
    updateUser: function () {
      const userId = this.$javalin.pathParams["user-id"];
      const url = `/api/users/${userId}`;
      axios.patch(url,
          {
            name: this.user.name,
            email: this.user.email,
            password: this.user.password
          })
          .then(response =>
              this.user.push(response.data))
          .catch(error => {
            console.log(error)
          })
      alert("User updated!")
      window.location.href='/users'
    },
    deleteUser: function () {
      if (confirm("Do you really want to delete?")) {
        const userId = this.$javalin.pathParams["user-id"];
        const url = `/api/users/remove-user/${userId}`
        axios.delete(url)
            .then(response => {
              alert("User deleted")
              //display the /users endpoint
              window.location.href = '/users';
            })
            .catch(function (error) {
              console.log(error)
            });
      }
    }
  }
});
</script>