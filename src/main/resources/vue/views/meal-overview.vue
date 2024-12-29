<template id="meal-overview">
  <app-layout>
    <div v-if="noMealFound">
      <p> We're sorry, we were not able to retrieve this meal.</p>
      <p> View <a :href="'/meals'">all meals</a>.</p>
    </div>
    <div class="card bg-light mb-3" v-if="!noMealFound">
      <div class="card-header">
        <div class="row">
          <div class="col-6"> Meal Details </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Update"
                    class="btn btn-info btn-simple btn-link"
                    @click="updateMeal()">
              <i class="far fa-save" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete"
                    class="btn btn-info btn-simple btn-link"
                    @click="deleteMeal()">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>

          </div>
        </div>
      </div>
      <div class="card-body">
        <form>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal-id">Meal ID</span>
            </div>
            <input type="number" class="form-control" v-model="meal.id" name="id" readonly placeholder="Id"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal-weight">Meal Type</span>
            </div>
            <input type="text" class="form-control" v-model="meal.mealType" name="mealType" placeholder="meal type"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal-duration">Food Item</span>
            </div>
            <input type="text" class="form-control" v-model="meal.foodItems" name="foodItem" placeholder="Food item"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal">Calories</span>
            </div>
            <input type="text" class="form-control" v-model="meal.calories" name="calories" placeholder="calories"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal">Protein</span>
            </div>
            <input type="text" class="form-control" v-model="meal.protein" name="protein" placeholder="protein"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal">Carbs</span>
            </div>
            <input type="text" class="form-control" v-model="meal.carbs" name="carbs" placeholder="carbs"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal-fat">Fat</span>
            </div>
            <input type="text" class="form-control" v-model="meal.fat" name="fat" placeholder="fat"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal-userid">Date</span>
            </div>
            <input type="text" class="form-control" v-model="meal.loggedAt" name="loggedAt" placeholder="date"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-meal-userid">User Id</span>
            </div>
            <input type="number" class="form-control" v-model="meal.userId" name="userid" placeholder="userid"/>
          </div>

        </form>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component("meal-overview", {
  template: "#meal-overview",
  data: () => ({
    user: null,
    noMealFound: false,
    meal: [],
  }),

  created: function () {
    const mealId = this.$javalin.pathParams["meal-id"];
    const url = `/api/meals/${mealId}`
    axios.get(url)
        .then(res => this.meal = res.data)
        .catch(error => {
          console.log("No meal found for id passed in the path parameter: " + error)
          this.noMealFound = true
        })
  },
  methods: {
    updateMeal: function () {
      const mealId = this.$javalin.pathParams["meal-id"];
      const url = `/api/meals/${mealId}`
      axios.patch(url,
          {
            userId: this.meal.userId,
            mealType: this.meal.mealType,
            foodItems: this.meal.foodItems,
            calories: this.meal.calories,
            protein: this.meal.protein,
            carbs: this.meal.carbs,
            fat: this.meal.fat,
            loggedAt: this.meal.loggedAt,
          })
          .then(response => {
            window.location.href ='/meals';
          })
          .catch(error => {
            console.log(error)
          })
      alert("meal updated!")
    },
    deleteMeal: function () {
      if (confirm("Do you really want to delete?")) {
        const mealsId = this.$javalin.pathParams["meal-id"];
        const url = `/api/remove-meals/${mealsId}`
        axios.delete(url)
            .then(response => {
              alert("Meal deleted")
              window.location.href = '/meals';
            })
            .catch(function (error) {
              console.log(error)
            });
      }
    }
  }
});
</script>