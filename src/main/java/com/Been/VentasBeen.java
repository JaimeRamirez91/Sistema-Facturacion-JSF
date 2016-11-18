package com.Been;

import com.DaoImp.DetalleProductoDaoImp;
import com.DaoImp.VentasDaoImp;
import com.DaoInterface.DetalleProductoDao;
import com.DaoInterface.ventasDAO;
import com.Util.HibernateUtil;
import com.entidades.DetalleFactura;
import com.entidades.Producto;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean(name = "ventasbeen")
@ViewScoped
public class VentasBeen implements Serializable {

    List<Producto> listaproducto;
    Session session = null;
    Transaction transaction = null;
    List<DetalleFactura> listadetallefactura = new ArrayList<>();
    private Producto producto;
    private String codBarra;

    public VentasBeen() {

    }

    public List<Producto> getListaproducto() {
        ventasDAO pDao = new VentasDaoImp();
        listaproducto = pDao.listaProducto();
        return listaproducto;
    }

    public void setListaproducto(List<Producto> listaproducto) {
        this.listaproducto = listaproducto;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getCodBarra() {
        return codBarra;
    }

    public void setCodBarra(String codBarra) {
        this.codBarra = codBarra;
    }

    public List<DetalleFactura> getListadetallefactura() {
        return listadetallefactura;
    }

    public void setListadetallefactura(List<DetalleFactura> listadetallefactura) {
        this.listadetallefactura = listadetallefactura;
    }

    //Metodo para agregar los datos del producto por el dialogo producto
    public void agregarDatosProducto(String codBarra) {

        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            DetalleProductoDao dDao = new DetalleProductoDaoImp();
            this.transaction = this.session.beginTransaction();
            //obtener los datos del cliente en el objeto cliente segus sea su codigo
            this.producto = dDao.obtenerProducto(session, codBarra);
            this.listadetallefactura.add(new DetalleFactura(this.producto));
            this.codBarra = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "correcto", "Producto agregado al detalle"));

            this.transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (this.transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {

                this.session.close();

            }
        }
    }
    //Metodo para agregar los datos del producto por codigo producto

     public void agregarDatosProductoCod(){
        this.session = null;
        this.transaction = null;

        try {
            if (this.codBarra == null) {
                
            } else {
                this.session = HibernateUtil.getSessionFactory().openSession();
                DetalleProductoDao dDao = new DetalleProductoDaoImp();
                this.transaction = this.session.beginTransaction();
                //obtener los datos del cliente en el objeto cliente segus sea su codigo
                this.producto = dDao.obtenerProducto(session, codBarra);
                
                if(this.producto != null){
                    this.listadetallefactura.add(new DetalleFactura(this.producto));
                    this.codBarra=null;
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se encontro el cliente", "ok"));
                }else{
                  this.codBarra=null;
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontro el registro", ";/"));
                }
                this.transaction.commit();
               
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (this.transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();

            }
        }
    }
     
     public String TotalVenta(){
         String total;
         double precio = 0;
         try {
             for(DetalleFactura d: listadetallefactura){
                precio = precio+ d.getProducto().getProPrecio();
             }
             
         } catch (Exception e) {
             total = "0";
         }
         total = String.format("%.2f", precio);
         return total;
     }
}
