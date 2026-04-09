<template>
  <div class="max-w-5xl mx-auto py-10 px-4">
    <div class="flex items-center justify-between mb-8">
      <UButton
        to="/prenotazioni"
        icon="i-lucide-chevron-left"
        variant="ghost"
        color="neutral"
      >
        Le mie prenotazioni
      </UButton>
      <div class="flex gap-2">
        <UButton icon="i-lucide-printer" variant="soft" color="neutral" @click="printReceipt">Stampa Ricevuta</UButton>
      </div>
    </div>

    <div v-if="pending" class="space-y-6">
      <USkeleton class="h-96 w-full rounded-3xl" />
      <div class="grid grid-cols-3 gap-6">
        <USkeleton class="h-32 col-span-2" />
        <USkeleton class="h-32" />
      </div>
    </div>

    <div v-else-if="prenotazione" class="grid grid-cols-1 lg:grid-cols-12 gap-8">
      
      <div class="lg:col-span-8 space-y-8">
        
        <div class="relative h-96 rounded-3xl overflow-hidden shadow-2xl">
          <img :src="prenotazione.pacchetto?.immagine" class="w-full h-full object-cover" />
          <div class="absolute inset-0 bg-linear-to-t from-black/80 via-black/20 to-transparent" />
          <div class="absolute bottom-8 left-8 text-white">
            <UBadge :color="getStatusColor(prenotazione.stato)" variant="solid" class="mb-4 uppercase font-black px-3 py-1">
              {{ prenotazione.stato }}
            </UBadge>
            <h1 class="text-4xl font-black">{{ prenotazione.pacchetto?.titolo }}</h1>
            <p class="flex items-center gap-2 text-zinc-300 mt-2">
              <UIcon name="i-lucide-map-pin" /> {{ prenotazione.pacchetto?.tags }}
            </p>
          </div>
        </div>

        <section>
          <h3 class="text-xl font-bold mb-3 italic text-zinc-800 dark:text-zinc-200">Informazioni sul viaggio</h3>
          <p class="text-zinc-600 dark:text-zinc-400 leading-relaxed text-lg">
            {{ prenotazione.pacchetto?.descrizione }}
          </p>
        </section>

        <UDivider />

        <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
          
          <section>
            <div class="flex items-center gap-2 mb-4">
              <div class="p-2 bg-primary/10 rounded-lg">
                <UIcon name="i-lucide-gift" class="text-primary w-5 h-5" />
              </div>
              <h3 class="font-bold text-lg">Sempre Inclusi</h3>
            </div>
            <ul class="space-y-3">
              <li v-for="s in serviziInclusi" :key="s.id" class="flex items-start gap-3 group">
                <UIcon name="i-lucide-check" class="text-green-500 mt-1" />
                <div>
                  <span class="font-semibold block group-hover:text-primary transition-colors">{{ s.nome }}</span>
                  <span class="text-xs text-zinc-500">{{ s.tipo }}</span>
                </div>
              </li>
              <li v-if="!serviziInclusi.length" class="text-zinc-400 text-sm italic">Nessun servizio standard incluso.</li>
            </ul>
          </section>

          <section>
            <div class="flex items-center gap-2 mb-4">
              <div class="p-2 bg-orange-100 dark:bg-orange-900/30 rounded-lg">
                <UIcon name="i-lucide-zap" class="text-orange-600 w-5 h-5" />
              </div>
              <h3 class="font-bold text-lg">Esperienze Extra</h3>
            </div>
            <ul class="space-y-3">
              <li v-for="s in serviziPagamento" :key="s.id" class="flex justify-between items-center p-3 bg-zinc-50 dark:bg-zinc-800/40 rounded-xl border border-zinc-100 dark:border-zinc-700">
                <div class="flex items-center gap-3">
                  <UIcon name="i-lucide-star" class="text-orange-400" />
                  <div>
                    <span class="font-semibold text-sm block">{{ s.nome }}</span>
                    <span class="text-[10px] uppercase text-zinc-500 font-bold tracking-widest">{{ s.tipo }}</span>
                  </div>
                </div>
                <UBadge variant="soft" color="neutral">+{{ s.prezzo }}€</UBadge>
              </li>
              <li v-if="!serviziPagamento.length" class="text-zinc-400 text-sm italic">Nessun servizio extra aggiunto.</li>
            </ul>
          </section>
        </div>
      </div>

      <div class="lg:col-span-4 space-y-6">
        <UCard class="sticky top-8 shadow-xl border-t-4 border-primary">
          <template #header>
            <h3 class="font-black text-center text-lg uppercase tracking-widest">Dettaglio Costi</h3>
          </template>

          <div class="space-y-4">
            <div class="flex justify-between">
              <span class="text-zinc-500 italic">Prezzo Base</span>
              <span class="font-medium text-zinc-900 dark:text-zinc-100">{{ prenotazione.pacchetto?.prezzo }}€</span>
            </div>
            
            <div v-if="totaleServiziExtra > 0" class="flex justify-between">
              <span class="text-zinc-500 italic">Servizi Extra</span>
              <span class="font-medium text-orange-600">+{{ totaleServiziExtra }}€</span>
            </div>

            <div class="pt-6 border-t border-dashed border-zinc-200 dark:border-zinc-700">
              <div class="flex justify-between items-baseline mb-2">
                <span class="text-sm font-bold uppercase text-zinc-400">Totale Pagato</span>
                <span class="text-4xl font-black text-primary">{{ prenotazione.prezzoPagato }}€</span>
              </div>
              <p class="text-[10px] text-zinc-400 text-right">IVA Inclusa</p>
            </div>
          </div>

          <template #footer>
            <div class="space-y-4">
              <div class="text-sm">
                <p class="font-bold mb-1">Dati Viaggiatore</p>
                <p class="text-zinc-600 dark:text-zinc-400 italic">
                  {{ prenotazione.cliente?.nome || 'Cliente #' + prenotazione.idCliente }}<br>
                  {{ prenotazione.cliente?.email }}
                </p>
              </div>
              <UButton block size="lg" icon="i-lucide-mail" variant="soft">Invia Voucher</UButton>
              <UButton 
                block 
                size="lg" 
                icon="i-lucide-printer" 
                variant="soft" 
                color="neutral" 
                @click="printReceipt"
              >
                Stampa Ricevuta
              </UButton>
              <UButton
                block
                color='error'
                variant="soft"
                icon="i-lucide-trash-2"
                label="Annulla Prenotazione"
                :loading="isDeleting"
                @click="handleDelete"
                class="mt-4"
                />
            </div>
          </template>
        </UCard>
      </div>

    </div>
  </div>
</template>

<script setup lang="ts">
const route = useRoute();
const idPrenotazione = route.params.id;
const isDeleting = ref(false);
const toast = useToast();

const { data: prenotazione, pending } = await useFetch<Prenotazione>(
  `http://localhost:8080/AgenziaViaggiApi/api/prenotazioni/${idPrenotazione}`
);

const serviziInclusi = computed(() => {
  return prenotazione.value?.pacchetto?.servizi?.filter(s => s.prezzo === 0) || [];
});

const serviziPagamento = computed(() => {
  return prenotazione.value?.pacchetto?.servizi?.filter(s => s.prezzo > 0) || [];
});

const totaleServiziExtra = computed(() => {
  return serviziPagamento.value.reduce((acc, s) => acc + s.prezzo, 0);
});

const formatDate = (dateStr?: string) => {
  if (!dateStr) return '-';
  const clean = dateStr.replace(/\[.*\]$/, '');
  return new Date(clean).toLocaleDateString('it-IT', { day: '2-digit', month: 'short', year: 'numeric' });
};

const getStatusColor = (stato: string) => {
  const s = stato?.toUpperCase();
  if (s === 'CONFERMATA') return 'primary';
  if (s === 'IN_ATTESA' || s === 'PENDENTE') return 'warning';
  return 'neutral';
};

async function handleDelete() {
  isDeleting.value = true;
  
  try {
    await $fetch(`http://localhost:8080/AgenziaViaggiApi/api/prenotazioni/${idPrenotazione}`, {
      method: 'DELETE'
    });

    toast.add({
      title: 'Prenotazione rimossa',
      icon: 'i-lucide-trash',
      color: 'success'
    });

    await navigateTo('/prenotazioni');

  } catch (error: any) {
    console.error("Errore:", error);
    toast.add({
      title: 'Errore',
      description: 'Impossibile eliminare la prenotazione al momento.',
      color: 'error'
    });
  } finally {
    isDeleting.value = false;
  }
}

const printReceipt = () => window.print();

useHead({
  title: `Viaggio: ${prenotazione.value?.pacchetto?.titolo || 'Prenotazione'}`
});
</script>