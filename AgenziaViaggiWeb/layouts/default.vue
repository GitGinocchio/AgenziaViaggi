<template>
  <div class="min-h-screen flex flex-col bg-white dark:bg-zinc-950">
    <UHeader>
      <template #title>
        <NuxtLink to="/" class="flex items-center gap-2 font-black text-xl tracking-tight text-primary">
          <UIcon name="i-lucide-palmtree" class="w-7 h-7" />
          <span>Agenzia<span class="text-zinc-900 dark:text-white">Viaggi</span></span>
        </NuxtLink>
      </template>

      <UNavigationMenu :items="headerItems" />

      <template #right>
        <UColorModeButton />
        
        <UButton
          v-if="!isUserRegistered"
          label="Accedi"
          variant="soft"
          color="primary"
          icon="i-lucide-log-in"
          size="sm"
          to="/onboarding"
        />
        
        <UButton
          v-else
          label="Prenota"
          icon="i-lucide-plus"
          size="sm"
          class="hidden sm:flex"
          to="/prenotazioni/new"
        />
      </template>

      <template #body>
        <UNavigationMenu :items="headerItems" orientation="vertical" class="-mx-2.5" />
      </template>
    </UHeader>

    <main class="grow">
      <UContainer class="py-8">
        <slot />
      </UContainer>
    </main>

    <UFooter>
      <template #left>
        <p class="text-zinc-500 text-sm">
          &copy; {{ new Date().getFullYear() }} Agenzia Viaggi
        </p>
      </template>
      <UNavigationMenu :items="footerItems" variant="link" />
      <template #right>
        <UButton icon="i-simple-icons-instagram" color="neutral" variant="ghost" />
        <UButton icon="i-simple-icons-facebook" color="neutral" variant="ghost" />
      </template>
    </UFooter>
  </div>
</template>

<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'

const isUserRegistered = ref(false)

onMounted(() => {
  isUserRegistered.value = !!localStorage.getItem('user_account')
})

const headerItems = computed<NavigationMenuItem[]>(() => {
  const baseItems = [
    { label: 'Dashboard', to: '/', icon: 'i-lucide-layout-dashboard' },
    { label: 'Catalogo', to: '/pacchetti', icon: 'i-lucide-palmtree' }
  ]

  // Aggiungiamo Prenotazioni solo se l'utente ha fatto l'onboarding
  if (isUserRegistered.value) {
    baseItems.push({
      label: 'Prenotazioni',
      to: '/prenotazioni',
      icon: 'i-lucide-ticket'
    })
  }

  return baseItems
})

const footerItems: NavigationMenuItem[] = [
  { label: 'Privacy Policy', to: '/privacy' },
  { label: 'Termini di Servizio', to: '/terms' },
  { label: 'Supporto', to: '/support' }
]
</script>