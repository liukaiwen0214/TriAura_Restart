import App from "@/App.vue";
import Aura from "@primeuix/themes/aura";
import Button from 'primevue/button';
import { router } from "@/router";
import { createApp } from "vue";
import PrimeVue from "primevue/config";
import "@/assets/index.css";
import "./style.css";;
import "primeicons/primeicons.css";
import "vue-sonner/style.css";





const app = createApp(App);
app.use(router);
app.component("Button", Button);
app.mount("#app");

app.use(PrimeVue, {
    // Default theme configuration
    theme: {
        preset: Aura,
        options: {
            prefix: 'p',
            darkModeSelector: 'system',
            cssLayer: false
        }
    }
 });