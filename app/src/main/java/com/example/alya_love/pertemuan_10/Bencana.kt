package com.example.alya_love.pertemuan_10

data class Bencana(
    val id: Int,
    val judul: String,
    val deskripsi: String,
    val lokasi: String,
    val tanggal: String,
    val gambar: String
)


//alur delete sama semua
//[START]
//│
//▼
//[RecyclerView Item → Klik btnHapus]
//│
//▼
//[AlertDialog.Builder(this)]
//│
//▼
//[.setTitle("Hapus Data")]
//│
//▼
//[.setMessage("Yakin ingin menghapus data ini?")]
//│
//▼
//[.setPositiveButton("Ya") { dialog, _ -> ... }]
//│
//▼
//[.setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }]
//│
//▼
//[.show()]
//│
//▼
//<User Pilih?>
//│
//├─[BATAL]──▶ [dialog.dismiss()] ──▶ [END]
//│
//└─[YA]
//│
//▼
//[lifecycleScope.launch]
//│
//▼
//[db.dao().delete(entity)]
//│
//▼
//[Toast "Data berhasil dihapus"]
//│
//▼
//[loadData() → Refresh RecyclerView]
//│
//▼
//[END]


//singkatnya
//CREATE:
//Fragment → FAB(+) → FormActivity → Input → Validasi → insert() → finish() → loadData()
//
//READ:
//Fragment.onViewCreated() → getAll() → dataList.addAll() → notifyDataSetChanged()
//
//UPDATE:
//RecyclerView → btnEdit → FormActivity(EXTRA_ID) → loadById() → Form Terisi → Ubah → update() → finish() → loadData()
//
//DELETE:
//RecyclerView → btnHapus → AlertDialog → Konfirmasi → delete() → Toast → loadData()