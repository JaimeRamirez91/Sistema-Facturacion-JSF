package com.Been;

import com.DaoImp.CategoriaDaoImp;
import com.DaoInterface.CategoriaDao;
import com.entidades.Categoria;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
@ManagedBean(name = "categoriabeen")
@ViewScoped
public class CategoriaBeen implements Serializable{
    List<Categoria> listaCategorias;
    public CategoriaBeen() {
    }

    public List<Categoria> getListaCategorias() {
        CategoriaDao cDao= new CategoriaDaoImp();
        listaCategorias =  cDao.listarCategoria();
        return listaCategorias;
    }

    public void setListaCategorias(List<Categoria> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }
       
}