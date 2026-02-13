import { createApp } from "vue";
import App from "@/App.vue";
import { router } from "@/router";
import "./style.css";

import PrimeVue from "primevue/config";
import Aura from "@primeuix/themes/aura";

import Button from 'primevue/button';
import Toast from 'primevue/toast';
import ToastService from "primevue/toastservice"

import "primeicons/primeicons.css";

const app = createApp(App);
app.use(router);
app.use(ToastService);
app.component("Button", Button);
app.component("Toast",Toast);
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