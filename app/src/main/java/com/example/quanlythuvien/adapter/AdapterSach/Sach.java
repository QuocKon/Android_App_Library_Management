package com.example.quanlythuvien.adapter.AdapterSach;

public class Sach {
    private int maSach;
    private String tenSach;
    private int maTheLoai;
    private String tomTat;
    private int matacGia;
    private String ngayXuatBan;
    private int manxb;
    private int soLuong;
    private byte[] hinhAnh;
    public Sach(){

    }

    public Sach(int maSach, String tenSach, int maTheLoai, String tomTat, int matacGia, String ngayXuatBan, int manxb, int soLuong, byte[] hinhAnh) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.maTheLoai = maTheLoai;
        this.tomTat = tomTat;
        this.matacGia = matacGia;
        this.ngayXuatBan = ngayXuatBan;
        this.manxb = manxb;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }
    public Sach(String tenSach, int maTheLoai, String tomTat, int matacGia, String ngayXuatBan, int manxb, int soLuong, byte[] hinhAnh) {
        this.tenSach = tenSach;
        this.maTheLoai = maTheLoai;
        this.tomTat = tomTat;
        this.matacGia = matacGia;
        this.ngayXuatBan = ngayXuatBan;
        this.manxb = manxb;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(int maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTomTat() {
        return tomTat;
    }

    public void setTomTat(String tomTat) {
        this.tomTat = tomTat;
    }

    public int getMatacGia() {
        return matacGia;
    }

    public void setMatacGia(int matacGia) {
        this.matacGia = matacGia;
    }

    public String getNgayXuatBan() {
        return ngayXuatBan;
    }

    public void setNgayXuatBan(String ngayXuatBan) {
        this.ngayXuatBan = ngayXuatBan;
    }

    public int getManxb() {
        return manxb;
    }

    public void setManxb(int manxb) {
        this.manxb = manxb;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    /*public static class MyAdapterSachDaMuona extends RecyclerView.Adapter<MyAdapterSachDaMuona.MyViewHolderMuonSach> {
        List<MuonSach> listmuonsach;
        Context mcontext;

        public MyAdapterSachDaMuona(Context mcontext) {
            this.mcontext = mcontext;
        }

        public void setData(List<MuonSach> list){
            this.listmuonsach = list;
            notifyDataSetChanged();
        }
        DAOSach daoSach = new DAOSach(mcontext);

        @NonNull
        @Override
        public MyViewHolderMuonSach onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quan_item_muon_sach, parent,false);
            return new MyViewHolderMuonSach(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolderMuonSach holder, int position) {
            MuonSach obj = listmuonsach.get(position);

            holder.txtngaymuonsach.setText("Ngày mượn: "+obj.getNgaymuon());

            int masach = obj.getMasach();
            holder.txttensachmuon.setText(daoSach.getTenSach(masach));
            holder.txtngaytrasach.setText("Ngày trả: "+obj.getNgaytra());
            holder.txttinhtrang.setText("Tình trạng: "+obj.getTinhtrang());
            holder.txtphuphi.setText("Phụ phí: "+obj.getPhuphi());
        }

        @Override
        public int getItemCount() {
            return listmuonsach.size();
        }


        public class MyViewHolderMuonSach extends RecyclerView.ViewHolder {
            TextView txttensachmuon, txtngaymuonsach, txtngaytrasach, txttinhtrang, txtphuphi;
            Button btntrasach;
            public MyViewHolderMuonSach(@NonNull View itemView) {
                super(itemView);
                txtngaymuonsach = itemView.findViewById(R.id.edtngaydamuon);
                txttensachmuon = itemView.findViewById(R.id.edttensachdamuon);
                txtngaytrasach = itemView.findViewById(R.id.edtngaytra);
                txttinhtrang = itemView.findViewById(R.id.edttinhtrangtra);
                txtphuphi = itemView.findViewById(R.id.edtphuphi);
                btntrasach = itemView.findViewById(R.id.btntrasach);
            }
        }
    }*/
}
