package com.Been;
import com.DaoImp.MenuDaoImp;
import com.DaoInterface.MenuDao;
import com.entidades.Menu;
import com.entidades.Usuarios;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.text.Document;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@ManagedBean
@SessionScoped

public class MenuBeen implements Serializable{
   
    private List<Menu> lista;
    private MenuModel model;
    MenuDao mDao = new MenuDaoImp();
    
    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();
        this.listarmenu();
        //this.EstablecerPermisos();
        //menu();
        ff();
    }
    
    public void listarmenu(){
        try {
             lista = mDao.listaMenu();
        } catch (Exception e) {
        }
    }

    public List<Menu> getLista() {
        return lista;
    }

    public void setLista(List<Menu> lista) {
        this.lista = lista;
    }

    public MenuDao getmDao() {
        return mDao;
    }

    public void setmDao(MenuDao mDao) {
        this.mDao = mDao;
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }
    /*
    public void EstablecerPermisos(){
        Usuarios us = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
      
           for(Menu m: lista){
              if(m.getTipo().equals("S") && m.getTipoUsruario().equals(us.getTipo().getTipoUsr())){
              DefaultSubMenu  firstsubmenu = new DefaultSubMenu(m.getNombre());
              for(Menu i: lista){
                 Menu submenu= i.getSubmenu();
                 if(submenu != null){
                    if(submenu.getCodigo() == m.getCodigo()){
                       DefaultMenuItem item = new DefaultMenuItem(i.getNombre());
                       item.setOutcome(i.getUrl());
                       
                       firstsubmenu.addElement(item);
                    }
                     
                 
                 }
              }
              model.addElement(firstsubmenu);
              }else{
                  if(m.getSubmenu() == null &&  m.getTipoUsruario().equals(us.getTipo().getTipoUsr())){
                  DefaultMenuItem item = new DefaultMenuItem(m.getNombre());
                  model.addElement(item);}
              
              }
           }
    }*/
    
    /*http://codepen.io/emredenx/pen/ojcxl*/
     public String ff(){
         boolean bandera = true;
         String menu = " "; 
        Usuarios us = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
      
           for(Menu m: lista){
              if(m.getTipo().equals("S") && m.getTipoUsruario().equals(us.getTipo().getTipoUsr())){
              //abrimos la lista
              if(bandera==true){
                  menu =menu+ " <li class='active'> <a href='"+m.getUrl()+"'>"+m.getNombre()+"</a>";
                  bandera = false;
              }else{
               menu =menu+ " <li> <a href='"+m.getUrl()+"'>"+m.getNombre()+"</a>";
              }
              
              DefaultSubMenu  firstsubmenu = new DefaultSubMenu(m.getNombre());
              menu =menu+"<ul>";
              for(Menu i: lista){
                
                 Menu submenu= i.getSubmenu();
                 
                 if(submenu != null){
                    if(submenu.getCodigo() == m.getCodigo()){
                         menu=menu+"<li><a href='"+i.getUrl()+"'>"+i.getNombre()+"</a></li>";
                       DefaultMenuItem item = new DefaultMenuItem(i.getNombre());
                      // item.setOutcome(i.getUrl());
                       
                       //firstsubmenu.addElement(item);
                    }
                     
                    
               
                 }
              }
              //cerramos la lista
              menu = menu +"</ul></li>";
           //   model.addElement(firstsubmenu);
             
              }else{
                  
                  /* Falta por definir*/
                  
                  if(m.getSubmenu() == null &&  m.getTipoUsruario().equals(us.getTipo().getTipoUsr())){
                  DefaultMenuItem item = new DefaultMenuItem(m.getNombre());
                   menu =menu+ " hola 2 <li> <a href='"+m.getUrl()+"'>"+m.getNombre()+"</a> </li> ++";
                  model.addElement(item);}
              
              }
           }
           //Se eliminan los ul que son inecesarios
           menu  = menu.replace("<ul></ul>"," ");
           //retorna el html renderisado
           return menu;
    }
     
   /* public String menu(){
        // String menu =  "<h1> hola mundo es una prueba </h1> http://www.w3schools.com/";
       
         String menu =   "<ul>"+
                             "<li><a class='active' href='../Inicio/Inicio.xhtml'>Inicio</a></li>"+ 
                             "<li><a href='../Ventas/Factura.xhtml'>Ventas</a></li>"+
                             "<li class='submenu'>"+
                              "<a href='#'><span class='icon-rocket'></span>Proyectos<span class='caret icon-arrow-down6'>"+
                              "</span></a>"+
					"<ul class='children'>"+
						"<li><a href='#'>SubElemento #1 <span class='icon-dot'></span></a></li>"+
							"</ul>"+
                 "</li>"+
                             "<li><a href='../Producto/Producto.xhtml'>Productos</a></li>"+
                        "</ul>"; 
         return menu;
    }*/
}