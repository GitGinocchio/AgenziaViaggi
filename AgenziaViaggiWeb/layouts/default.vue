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
          :to="{ path: '/onboarding', query: { redirect: route.fullPath } }"
        />
        
        <UDropdownMenu
          v-else
          :items="userDropdownItems"
          :popper="{ placement: 'bottom-end' }"
        >
          <UButton
            color="neutral"
            variant="ghost"
            icon="i-lucide-user"
            label="Area utente"
            trailing-icon="i-lucide-chevron-down"
          />
        </UDropdownMenu>
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
import type { DropdownMenuItem, NavigationMenuItem } from '@nuxt/ui'

const route = useRoute()
const userAccount = ref<Cliente|null>(null)

const loadAccount = () => {
  if (import.meta.client) {
    const data = localStorage.getItem('user_account')
    userAccount.value = data ? JSON.parse(data) : null
  }
}

onMounted(loadAccount)

const isUserRegistered = computed(() => !!userAccount.value)


const headerItems = computed<NavigationMenuItem[]>(() => {
  const baseItems = [
    { label: 'Dashboard', to: '/', icon: 'i-lucide-layout-dashboard' },
    { label: 'Catalogo', to: '/pacchetti', icon: 'i-lucide-palmtree' }
  ]

  if (isUserRegistered.value) {
    baseItems.push({
      label: 'Prenotazioni',
      to: '/prenotazioni',
      icon: 'i-lucide-ticket'
    })
  }

  return baseItems
})

const userDropdownItems = computed<DropdownMenuItem[][]>(() => [
  [
    {
      label: userAccount.value?.nome || 'Utente',
      slot: 'account',
      disabled: true
    },
    {
      label: userAccount.value?.email || 'Email',
      slot: 'account',
      disabled: true
    }
  ],
  [
    {
      label: 'Elimina Account',
      icon: 'i-lucide-trash-2',
      onSelect: () => handleDeleteAccount()
    }
  ]
])

async function handleDeleteAccount() {
  if (!userAccount.value?.id) return
  
  try {
    await $fetch(`http://localhost:8080/AgenziaViaggiApi/api/clienti/${userAccount.value.id}`, {
      method: 'DELETE'
    });

    localStorage.removeItem('user_account')
    userAccount.value = null
    
    await navigateTo('/')
  } catch (e) {
    console.error(e)
  }
}

const footerItems: NavigationMenuItem[] = [
  { label: 'Privacy Policy', to: '/privacy' },
  { label: 'Termini di Servizio', to: '/terms' },
  { label: 'Supporto', to: '/support' }
]
</script>