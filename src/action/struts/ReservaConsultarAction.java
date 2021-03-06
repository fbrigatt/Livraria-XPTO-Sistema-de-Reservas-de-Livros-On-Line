/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action.struts;

import action.form.bean.ReservaForm;
import action.form.bean.UsuarioForm;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Livro;
import modelos.Reserva;
import modelos.Usuario;
import modelos.UsuarioVip;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import persistencia.DAOFactory;
import persistencia.GenericDAO;

/**
 *
 * @author Daniel Dias
 */
public class ReservaConsultarAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    //private static final String SUCCESS = "success";
    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return ConsultarReservaForm
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        Reserva reserva = null;
        String msg = null;
        Usuario usuario = null;
        DAOFactory df = null;
        Livro livro = null;

        GenericDAO<Reserva> daoReserva = null;
        GenericDAO<Usuario> daoUsuario = null;
        GenericDAO<Livro> daoLivro = null;

        try {
            
           
            df = DAOFactory.getDaoFactory(DAOFactory.HIBERNATE);
            daoLivro = (GenericDAO<Livro>) df.getGenericoDAOLivroHibernate();
            daoReserva = (GenericDAO<Reserva>) df.getGenericoDAOReservaHibernate();
            daoUsuario = (GenericDAO<Usuario>) df.getGenericoDAOUsuarioHibernate();

            reserva = new Reserva();
            usuario = new UsuarioVip();

            usuario.setLogin(request.getParameter("login"));
          
            request.setAttribute("usuario", daoUsuario.consultar(usuario) );

            reserva.setUsuario(usuario);
            usuario.addReserva(reserva);

            request.setAttribute("al", daoReserva.getUser(reserva));

            msg = "Consulta de Reserva realizada com sucesso";

        } catch (ClassNotFoundException e) {
            msg = "Erro de Driver";
            e.printStackTrace();
        } catch (SQLException e) {
            msg = "Erro de SQL";
            e.printStackTrace();
        } catch (Exception e) {
            msg = "Erro generico";
            e.printStackTrace();
        }

        request.setAttribute("msg", msg);
        return mapping.findForward("sucessoConsultaReserva");
    }
}
