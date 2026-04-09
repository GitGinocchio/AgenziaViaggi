<template>
  <div class="space-y-16 pb-20">
    <section class="text-center py-12 bg-linear-to-b from-primary-50/50 dark:from-primary-950/20 to-transparent rounded-3xl">
      <UBadge variant="subtle" color="primary" class="mb-4 uppercase tracking-widest">Esplora il Mondo</UBadge>
      <h1 class="text-5xl md:text-6xl font-black tracking-tight mb-6">
        Il tuo prossimo viaggio <br />
        <span class="text-primary">inizia qui.</span>
      </h1>
      <p class="text-lg text-zinc-600 dark:text-zinc-400 max-w-2xl mx-auto mb-8">
        Scopri i nostri pacchetti esclusivi curati dai nostri esperti. Dai paradisi tropicali alle vette innevate, abbiamo la soluzione perfetta per te.
      </p>
      <div class="flex justify-center gap-4">
        <UButton size="xl" icon="i-lucide-search" @click="router.push('/pacchetti')">Scopri Pacchetti</UButton>
        <UButton size="xl" variant="ghost" color="neutral">Parla con un esperto</UButton>
      </div>
    </section>

    <section>
      <div class="flex justify-between items-end mb-8">
        <div>
          <h2 class="text-3xl font-bold">Pacchetti in Evidenza</h2>
          <p class="text-zinc-500">Le mete più amate dai nostri viaggiatori</p>
        </div>
        <UButton variant="link" color="primary" trailing-icon="i-lucide-arrow-right" @click="router.push('/pacchetti')">Vedi tutti</UButton>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <UCard 
          v-for="p in pacchettiRandom" 
          :key="p.id"
          class="group overflow-hidden transition-all hover:shadow-xl dark:hover:shadow-primary/10 border-none ring-1 ring-zinc-200 dark:ring-zinc-800"
        >
          <div class="aspect-video bg-zinc-100 dark:bg-zinc-800 relative overflow-hidden">
            <img :src="p.immagine" class="object-cover w-full h-full group-hover:scale-105 transition-transform duration-500" />
            <div v-if="p.isOfferta" class="absolute top-4 right-4">
              <UBadge color="warning" variant="solid" class="font-bold">OFFERTA</UBadge>
            </div>
          </div>

          <div class="p-6">
            <div class="flex justify-between items-start mb-2">
              <h3 class="text-xl font-bold">{{ p.titolo }}</h3>
              <div class="text-right">
                <span class="block text-sm text-zinc-500 line-through" v-if="p.isOfferta">{{ p.prezzo + 200 }}€</span>
                <span class="text-xl font-black text-primary">{{ p.prezzo }}€</span>
              </div>
            </div>
            <p class="text-zinc-600 dark:text-zinc-400 text-sm line-clamp-2 mb-6">
              {{ p.descrizione }}
            </p>
            
            <div v-if="p.tags" class="flex gap-2 mb-6">
              <UBadge v-for="tag in p.tags.split(',')" :key="tag" variant="outline" color="neutral" size="sm">
                {{ tag }}
              </UBadge>
            </div>

            <UButton block color="primary" variant="solid" @click="router.push(`/pacchetti/${p.id}`)">Dettagli Viaggio</UButton>
          </div>
        </UCard>
      </div>
    </section>

    <section class="bg-zinc-100 dark:bg-zinc-900 -mx-4 px-4 py-16 rounded-[3rem]">
      <UContainer>
        <div class="text-center mb-12">
          <h2 class="text-3xl font-bold">Viaggia secondo il tuo stile</h2>
        </div>
        <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
          <UButton 
            v-for="cat in categorie" 
            :key="cat.name"
            @click="router.push(cat.url)"
            variant="soft" 
            color="neutral" 
            class="h-32 flex flex-col items-center justify-center gap-3 text-lg font-bold hover:bg-primary-100 dark:hover:bg-primary-900/30 transition-colors"
          >
            <UIcon :name="cat.icon" class="w-8 h-8 text-primary" />
            {{ cat.name }}
          </UButton>
        </div>
      </UContainer>
    </section>

    <section class="max-w-4xl mx-auto text-center">
      <h2 class="text-2xl font-bold mb-8">Servizi Extra inclusi nei nostri pacchetti</h2>
      <div class="flex flex-wrap justify-center gap-8">
        <div class="flex flex-col items-center gap-2">
          <div class="w-12 h-12 rounded-full bg-primary/10 flex items-center justify-center text-primary">
            <UIcon name="i-lucide-plane" class="w-6 h-6" />
          </div>
          <span class="font-medium">Voli Premium</span>
        </div>
        <div class="flex flex-col items-center gap-2">
          <div class="w-12 h-12 rounded-full bg-primary/10 flex items-center justify-center text-primary">
            <UIcon name="i-lucide-shield-check" class="w-6 h-6" />
          </div>
          <span class="font-medium">Assicurazione</span>
        </div>
        <div class="flex flex-col items-center gap-2">
          <div class="w-12 h-12 rounded-full bg-primary/10 flex items-center justify-center text-primary">
            <UIcon name="i-lucide-headphones" class="w-6 h-6" />
          </div>
          <span class="font-medium">Assistenza 24/7</span>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
const router = useRouter();

const { data: pacchetti, pending } = await useFetch<Pacchetto[]>('http://localhost:8080/AgenziaViaggiApi/api/pacchetti');

const pacchettiRandom = computed(() => {
  if (!pacchetti.value || pacchetti.value.length === 0) return [];

  const inOfferta = pacchetti.value.filter(p => p.isOfferta);
  const normali = pacchetti.value.filter(p => !p.isOfferta);

  const shuffle = (array: Pacchetto[]) => [...array].sort(() => Math.random() - 0.5);

  const offerteShuffled = shuffle(inOfferta);
  const normaliShuffled = shuffle(normali);

  const risultato = offerteShuffled.slice(0, 3);

  if (risultato.length < 3) {
    const mancanti = 3 - risultato.length;
    risultato.push(...normaliShuffled.slice(0, mancanti));
  }

  return risultato;
});

const categorie = [
  { name: 'Mare', icon: 'i-lucide-waves', url: "/pacchetti?q=Mare" },
  { name: 'Montagna', icon: 'i-lucide-mountain', url: "/pacchetti?q=Montagna" },
  { name: 'Città d\'arte', icon: 'i-lucide-landmark', url: "/pacchetti?q=Città d\'arte" },
  { name: 'Avventura', icon: 'i-lucide-compass', url: "/pacchetti?q=Avventura" }
];
</script>