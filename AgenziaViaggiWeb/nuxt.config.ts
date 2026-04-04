// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  css: ['~/assets/css/main.css'],
  modules: ['@nuxt/ui'],
  compatibilityDate: '2025-07-15',
  devtools: { enabled: false },

  runtimeConfig: {
    public: {
      apiBase: 'http://localhost:8080/AgenziaViaggiApi/api'
    }
  },

  devServer: { 
    host: '127.0.0.1', 
    port: 3000 
  },

  vite: { 
    server: {
      strictPort: true, 
      hmr: { 
        port: 3000, 
        clientPort: 3000 
      } 
    } 
  }
})
