<template>
  <div class="min-h-screen bg-zinc-50/50 dark:bg-zinc-950">
    <section class="relative py-16 bg-white dark:bg-zinc-900 border-b border-zinc-200 dark:border-zinc-800">
      <UContainer>
        <div class="text-center max-w-3xl mx-auto mb-10">
          <UBadge variant="subtle" color="primary" class="mb-4">Catalogo {{ new Date().getFullYear() }}</UBadge>
          <h1 class="text-4xl md:text-5xl font-black tracking-tight mb-4">
            Trova la tua prossima <span class="text-primary">Avventura</span>
          </h1>
          <p class="text-zinc-500 text-lg">
            Esplora centinaia di destinazioni verificate e pacchetti tutto incluso.
          </p>
        </div>

        <div class="max-w-4xl mx-auto relative group">
          <UInput
            v-model="searchQuery"
            icon="i-lucide-search"
            size="xl"
            class="w-full shadow-[0_20px_50px_rgba(0,0,0,0.05)] dark:shadow-primary/5 transition-all"
            input-class="text-xl border-none ring-1 ring-zinc-200 dark:ring-zinc-800 focus:ring-2 focus:ring-primary bg-white dark:bg-zinc-900"
            placeholder="Cerca per meta, attività o clima (es: mare, neve, Giappone...)"
            @input="debounceSearch" 
            @keydown.enter="handleSearch"
          >
            <template #trailing>
              <UButton
                color="primary"
                size="xs"
                label="Cerca ora"
                class="rounded-xl px-8 font-bold mr-1 hidden md:flex"
                @click="refresh()"
              />
            </template>
          </UInput>
        </div>

        <div class="flex justify-center gap-4 mt-8 overflow-x-auto pb-2 px-4 no-scrollbar">
          <UButton
            v-for="cat in quickFilters"
            :key="cat.label"
            variant="ghost"
            color="neutral"
            class="shrink-0 flex flex-col items-center gap-1 hover:text-primary transition-colors"
            @click="setQuickSearch(cat.value); debounceSearch()"
          >
            <UIcon :name="cat.icon" class="w-6 h-6" />
            <span class="text-xs font-medium">{{ cat.label }}</span>
          </UButton>
        </div>
      </UContainer>
    </section>

    <UContainer class="py-12">
      <div class="flex justify-between items-center mb-8">
        <h2 class="text-xl font-bold text-zinc-900 dark:text-white">
          {{ pending ? 'Ricerca in corso...' : `${pacchetti?.length || 0} pacchetti trovati` }}
        </h2>
        <USelectMenu 
          v-model="sortBy" 
          :options="['Prezzo: Crescente', 'Prezzo: Decrescente', 'Più recenti']" 
          variant="none"
          class="w-48"
        />
      </div>

      <div v-if="pending" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <UCard v-for="i in 6" :key="i" class="overflow-hidden border-none ring-1 ring-zinc-200 dark:ring-zinc-800">
          <USkeleton class="h-56 w-full" />
          <div class="p-6 space-y-4">
            <USkeleton class="h-6 w-3/4" />
            <USkeleton class="h-4 w-full" />
            <USkeleton class="h-10 w-full rounded-xl" />
          </div>
        </UCard>
      </div>

      <div v-else-if="!pacchetti || pacchetti?.length === 0" class="text-center py-24 bg-white dark:bg-zinc-900 rounded-3xl border border-dashed border-zinc-300 dark:border-zinc-700">
        <UIcon name="i-lucide-map" class="w-16 h-16 mx-auto text-zinc-300 mb-4" />
        <h3 class="text-2xl font-bold">Nessuna meta trovata</h3>
        <p v-if="searchQuery == ''" class="text-zinc-500 mb-6">Non abbiamo trovato pacchetti disponibili, riprova piu tardi!</p>
        <p v-else class="text-zinc-500 mb-6">Non abbiamo trovato pacchetti per "{{ searchQuery }}". Prova un altro termine!</p>
        <UButton v-if="searchQuery != ''" color="primary" variant="soft" @click="searchQuery = ''; refresh()">Mostra tutto il catalogo</UButton>
      </div>

      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <UCard 
          v-for="p in pacchetti" 
          :key="p.id"
          class="group overflow-hidden transition-all hover:-translate-y-1 hover:shadow-2xl border-none ring-1 ring-zinc-200 dark:ring-zinc-800"
        >
          <div class="aspect-16/10 relative overflow-hidden">
            <img 
              :src="p.immagine || `https://source.unsplash.com/featured/?${p.titolo},travel`" 
              class="object-cover w-full h-full group-hover:scale-110 transition-transform duration-700" 
            />
            <div class="absolute inset-0 bg-linear-to-t from-black/60 to-transparent opacity-0 group-hover:opacity-100 transition-opacity" />
            
            <div v-if="p.isOfferta" class="absolute top-4 left-4">
              <UBadge color="warning" variant="solid" class="font-black animate-pulse shadow-lg">PROMO</UBadge>
            </div>
          </div>

          <div class="p-6">
            <div class="flex justify-between items-start mb-3">
              <div>
                <h3 class="text-xl font-bold group-hover:text-primary transition-colors">{{ p.titolo }}</h3>
                <div class="flex flex-wrap gap-1 mt-2">
                  <span v-for="tag in p.tags?.split(',')" :key="tag" class="text-[10px] uppercase tracking-wider font-bold text-zinc-400 bg-zinc-100 dark:bg-zinc-800 px-2 py-0.5 rounded">
                    {{ tag.trim() }}
                  </span>
                </div>
              </div>
              <div class="text-right">
                <p class="text-2xl font-black text-primary">{{ p.prezzo }}€</p>
                <p class="text-[10px] text-zinc-400 uppercase">a persona</p>
              </div>
            </div>

            <p class="text-zinc-600 dark:text-zinc-400 text-sm line-clamp-2 mb-6">
              {{ p.descrizione }}
            </p>

            <UButton 
              block 
              size="lg"
              color="primary" 
              variant="solid" 
              class="rounded-xl shadow-md"
              :to="`/pacchetti/${p.id}`"
            >
              Scopri di più
            </UButton>
          </div>
        </UCard>
      </div>
    </UContainer>
  </div>
</template>

<script setup lang="ts">
const route = useRoute();
const router = useRouter();
const searchQuery = ref((route.query.q as string) || '');
const sortBy = ref('Più recenti');

const quickFilters = ref([
  { label: 'Mare', value: 'mare', icon: 'i-lucide-palmtree' },
  { label: 'Montagna', value: 'montagna', icon: 'i-lucide-mountain' },
  { label: 'Città', value: 'città', icon: 'i-lucide-landmark' },
  { label: 'Neve', value: 'neve', icon: 'i-lucide-snowflake' },
  { label: 'Relax', value: 'relax', icon: 'i-lucide-sparkles' },
]);

onMounted(() => {
  const userData = localStorage.getItem('user_account');
  if (userData) {
    try {
      const user = JSON.parse(userData);
      if (user.preferenze) {
        const prefsArray = user.preferenze.split(',').map((p: string) => p.trim());

        prefsArray.forEach((pref: string) => {
          const val = pref.toLowerCase();
          
          const exists = quickFilters.value.some(f => f.value === val);

          if (!exists && pref !== "") {
            quickFilters.value.push({
              label: pref,
              value: val,
              icon: getIconByPref(pref)
            });
          }
        });
      }
    } catch (e) {
      console.error("Errore nel parsing delle preferenze", e);
    }
  }
});

const getIconByPref = (pref: string) => {
  const p = pref.toLowerCase();
  if (p.includes('mare')) return 'i-lucide-palmtree';
  if (p.includes('montagna')) return 'i-lucide-mountain';
  if (p.includes('città')) return 'i-lucide-landmark';
  if (p.includes('neve')) return 'i-lucide-snowflake';
  if (p.includes('avventura')) return 'i-lucide-compass';
  if (p.includes('crocier')) return 'i-lucide-ship';
  return 'i-lucide-map-pin'; 
};

const apiUrl = computed(() => {
  const base = 'http://localhost:8080/AgenziaViaggiApi/api/pacchetti';
  return searchQuery.value.trim() !== '' ? `${base}/search` : base;
});

const { data: pacchetti, pending, refresh } = await useFetch<Pacchetto[]>(apiUrl, {
  query: computed(() => searchQuery.value ? { q: searchQuery.value } : {}),
  watch: false,
  key: 'pacchetti-list'
});

const handleSearch = () => {
  router.push({ query: { q: searchQuery.value || undefined } });
  refresh();
};

let timeout : number;
const debounceSearch = () => {
  clearTimeout(timeout);
  timeout = setTimeout(() => {
    handleSearch();
  }, 300);
};

const setQuickSearch = (val: string) => {
  searchQuery.value = val;
  handleSearch();
};

const formatDate = (dateStr?: string) => {
  if (!dateStr) return '';
  return new Date(dateStr).toLocaleDateString('it-IT', { month: 'short', year: 'numeric' });
};
</script>

<style scoped>
.no-scrollbar::-webkit-scrollbar { display: none; }
.no-scrollbar { -ms-overflow-style: none; scrollbar-width: none; }
</style>