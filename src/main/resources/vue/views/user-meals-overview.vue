<template id="user-meals-overview">
  <app-layout>
    <div v-if="noMealFound">
      <p>No meals found for this user.</p>
      <p> View <a :href="'/users'">all user meal</a>.</p>
    </div>
    <div class="card bg-light mb-3" v-if="!noMealFound">
      <div class="card-header">
        <div class="row">
          <div class="col-6"> User Meals </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Add Bmi"
                    class="btn btn-info btn-simple btn-link"
                    @click="hideForm =!hideForm">
              <i class="fas fa-plus" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Refresh Meals"
                    class="btn btn-success btn-simple btn-link"
                    @click="fetchMeals()">
              <i class="fas fa-sync" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
      <div class="card-body" :class="{ 'd-none': hideForm}">
        <form>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal-mealType">Meal Type</span>
            </div>
            <input type="text" class="form-control" v-model="formData.mealType" name="mealType" placeholder="Meal type"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal-foodItems">Food Item</span>
            </div>
            <input type="text" class="form-control" v-model="formData.foodItems" name="foodItems" placeholder="foodItem"/>
          </div>

          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal-calories">Calories</span>
            </div>
            <input type="number" class="form-control" v-model="formData.calories" name="calories" placeholder="calories"/>
          </div>

          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal-protein">protein</span>
            </div>
            <input type="number" class="form-control" v-model="formData.protein" name="protein" placeholder="protein"/>
          </div>

          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal-carb">Carbs</span>
            </div>
            <input type="number" class="form-control" v-model="formData.carbs" name="carbs" placeholder="carbs"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal-fat">fat</span>
            </div>
            <input type="number" class="form-control" v-model="formData.fat" name="fat" placeholder="fat"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal-calories">Date</span>
            </div>
            <input type="datetime-local" class="form-control" v-model="formData.loggedAt" name="loggedAt" placeholder="date"/>
          </div>

          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-id">User ID</span>
            </div>
            <input type="number" class="form-control" v-model="formData.userId" name="id"  placeholder="Id"/>
          </div>
        </form>
        <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addMeal()">Submit</button>
      </div>
      <div class="card-body">
        <ul>
          <li v-for="meal in meal" :key="meal.id">
            Meal Type: {{ meal.mealType }}, Food Items: {{ meal.foodItems }}, Calories: {{ meal.calories }},
            protein: {{ meal.protein }},carbs: {{ meal.carbs }},fat: {{ meal.fat }},Date: {{ meal.loggedAt }},
            ({{ getUserName(meal.userId) }})
            <a :href="`/meals/${meal.id}`">
              <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="updateMeal(meal.id)">
                <i class="fa fa-pencil" aria-hidden="true"></i>
              </button>
            </a>
            <button rel="tooltip" title="Delete"
                    class="btn btn-info btn-simple btn-link" @click="deleteMeal(meal.id)">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </li>
        </ul>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component("user-meals-overview", {
  template: "#user-meals-overview",
  data: () => ({
    meal: [],
    noMealFound: false,
    formData: [],
    users: {},
    hideForm: true,
  }),
  created: function () {
    this.fetchMeals();
    this.fetchUsers();
  },
  methods: {
    fetchMeals: function () {
      axios.get(`/api/meals`)
          .then(res => this.meal = res.data)
          .catch(() => alert("Error while fetching meal"));
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
    deleteMeal: function (mealId) {
      if (confirm("Do you want to delete this meal?")) {
        const url = `/api/remove-meals/${mealId}`
        axios.delete(url)
            .then(response => {
              alert("Bmi Deleted")
              window.location.href = '/meals'
            })
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    addMeal: function () {
      const url = '/api/add-meals';
      axios.post(url,
          {
            mealType: this.formData.mealType,
            foodItems: this.formData.foodItems,
            calories: this.formData.calories,
            protein: this.formData.protein,
            carbs: this.formData.carbs,
            fat: this.formData.fat,
            loggedAt: this.formData.loggedAt,
            userId: this.formData.userId
          })
          .then(response => {
            this.hideForm = true;
            this.formData = {};
            this.fetchMeals();
          })
          .catch(error => {
            console.log(error)
          })
    }
  },
});
</script>