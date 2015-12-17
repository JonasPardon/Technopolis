/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ehb.database.dao;
import be.ehb.database.model.Zone;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ruben_000
 */
public class ZoneDao {
    
            public static int getIdVanZoneOpNummer(int nummer){
            int id = 0;
		try {
                    ResultSet mijnResultset = Database.voerSqlUitEnHaalResultaatOp("SELECT Id from tblZones where Zonenummer = ?", new Object[] { nummer });
                    if (mijnResultset != null) {
                        mijnResultset.first();
                        id = mijnResultset.getInt("Id");
                            
                    }

                    
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
            return id;

            
        }

    
}
