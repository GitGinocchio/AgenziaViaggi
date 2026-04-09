<template>
  <div class="space-y-8">
    <div class="flex flex-col md:flex-row md:items-end justify-between gap-4">
      <div>
        <h1 class="text-3xl font-black tracking-tight text-zinc-900 dark:text-white">
          I miei <span class="text-primary">Viaggi</span>
        </h1>
        <p class="text-zinc-500 mt-1">Cronologia e dettagli delle tue prenotazioni.</p>
      </div>
      <UButton 
        to="/pacchetti" 
        icon="i-lucide-search" 
        label="Trova un nuovo viaggio" 
        size="lg"
      />
    </div>

    <div v-if="pending" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <UCard v-for="i in 3" :key="i" class="overflow-hidden">
        <USkeleton class="h-48 w-full" />
        <div class="p-4 space-y-3">
          <USkeleton class="h-6 w-3/4" />
          <USkeleton class="h-4 w-1/2" />
        </div>
      </UCard>
    </div>

    <div v-else-if="prenotazioni?.length" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <UCard 
        v-for="p in prenotazioni" 
        :key="p.id" 
        class="group cursor-pointer hover:ring-2 hover:ring-primary transition-all overflow-hidden flex flex-col"
        @click="navigateTo(`/prenotazioni/${p.id}`)"
      >
        <template #header>
          <div class="relative -m-4 h-48 overflow-hidden">
            <img 
              :src="p.pacchetto?.immagine || 'https://images.unsplash.com/photo-1488085061387-422e29b40080?q=80&w=1000&auto=format&fit=crop'" 
              class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-110"
            />
            <div class="absolute top-4 right-4">
              <UBadge :color="getStatusColor(p.stato)" variant="solid" class="shadow-lg">
                {{ p.stato || 'IN ELABORAZIONE' }}
              </UBadge>
            </div>
          </div>
        </template>

        <div class="space-y-4">
          <div>
            <h3 class="font-bold text-xl leading-tight">{{ p.pacchetto?.titolo || 'Dettagli Viaggio' }}</h3>
            <div class="flex items-center gap-1 text-zinc-500 text-sm mt-1">
              <UIcon name="i-lucide-calendar" />
              Prenotato il {{ formatDate(p.dataPrenotazione) }}
            </div>
          </div>

          <div class="flex justify-between items-center pt-4 border-t border-zinc-100 dark:border-zinc-800">
            <div class="flex flex-col">
              <span class="text-xs text-zinc-400 uppercase font-bold">Totale Pagato</span>
              <span class="text-xl font-black text-primary">{{ p.prezzoPagato }}€</span>
            </div>
            <UButton 
              label="Dettagli" 
              variant="ghost" 
              trailing-icon="i-lucide-arrow-right" 
              color="neutral"
            />
          </div>
        </div>
      </UCard>
    </div>

    <div v-else class="flex flex-col items-center justify-center py-20 bg-zinc-50 dark:bg-zinc-900 rounded-3xl border-2 border-dashed border-zinc-200 dark:border-zinc-800 text-center">
      <div class="bg-white dark:bg-zinc-800 p-6 rounded-full shadow-xl mb-6">
        <UIcon name="i-lucide-luggage" class="w-12 h-12 text-primary" />
      </div>
      <h2 class="text-2xl font-bold">Ancora nessuna avventura?</h2>
      <p class="text-zinc-500 max-w-xs mx-auto mt-2 mb-8">
        Le tue prenotazioni appariranno qui una volta scelto il tuo prossimo viaggio.
      </p>
      <UButton label="Inizia a esplorare" size="xl" to="/pacchetti" />
    </div>
  </div>
</template>

<script setup lang="ts">
const columns = [
  { key: 'pacchetto', label: 'Viaggio' },
  { key: 'data', label: 'Data Prenotazione' },
  { key: 'prezzo', label: 'Totale' },
  { key: 'stato', label: 'Stato' },
  { key: 'actions', label: '' }
];

const userId = ref<number | null>(null);

onMounted(() => {
  const user = JSON.parse(localStorage.getItem('user_account') || '{}');
  if (user.id) {
    userId.value = user.id;
    refresh();
  } else {
    navigateTo('/onboarding');
  }
});

const { data: prenotazioni, pending, refresh } = await useFetch<Prenotazione[]>(() => 
  `http://localhost:8080/AgenziaViaggiApi/api/prenotazioni/cliente/${userId.value}`, 
  {
    immediate: false,
    watch: [userId]
  }
);

const getStatusColor = (stato: string) => {
  switch (stato?.toLowerCase()) {
    case 'confermata': return 'primary';
    case 'in_attesa': return 'warning';
    case 'annullata': return 'error';
    default: return 'neutral';
  }
};

const formatDate = (dateStr?: string) => {
  if (!dateStr) return '-';

  const cleanDateStr = dateStr.replace(/\[.*\]$/, '');

  const date = new Date(cleanDateStr);

  if (isNaN(date.getTime())) return 'Data non valida';

  return date.toLocaleDateString('it-IT', {
    day: '2-digit',
    month: 'long',
    year: 'numeric'
  });
};
</script>