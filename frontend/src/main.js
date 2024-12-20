import Vue from "vue";
import App from "./App.vue"; // Assuming you have an App.vue component or similar

new Vue({
  render: (h) => h(App),
}).$mount("#app"); // Mount to a div with id="app" in your index.html
