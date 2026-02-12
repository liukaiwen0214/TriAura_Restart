import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import tsconfigPaths from "vite-tsconfig-paths";
import { fileURLToPath, URL } from "node:url";

export default defineConfig({
    plugins: [
        vue(),
        tsconfigPaths({
            projects: ["./tsconfig.app.json"], // ✅ 关键
        }),
    ],
    resolve: {
        alias: {
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
    },
    server: {
        proxy: {
            "/api": {
                target: "http://localhost:8080",
                changeOrigin: true,
            },
        },
    },
});