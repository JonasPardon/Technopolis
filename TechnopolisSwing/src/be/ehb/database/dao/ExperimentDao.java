/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ehb.database.dao;
import java.sql.*;
import java.util.ArrayList;

import be.ehb.database.model.Experiment;
/**
 *
 * @author MaartenH
 */
public class ExperimentDao {
    public static ArrayList<Experiment> getExperimenten() {
		ArrayList<Experiment> resultaat = new ArrayList<Experiment>();
		try {
			ResultSet mijnResultset = Database.voerSqlUitEnHaalResultaatOp("SELECT e.Id, e.Nummer, e.Actief, z.Nummer from tblExperimenten e inner join tblZones z on e.Zone_Id = z.Id");
                    
			//ResultSet mijnResultset = Database.voerSqlUitEnHaalResultaatOp("SELECT e.Id, e.Nummer, e.Actief, e.Zone_Id from tblExperimenten e");
			if (mijnResultset != null) {
				while (mijnResultset.next()) {
					Experiment huidigExperiment = converteerHuidigeRijNaarObject(mijnResultset);
					resultaat.add(huidigExperiment);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}

		return resultaat;
	}

    public static Experiment getExperimentById(int id) {
		Experiment resultaat = null;
		try {
			ResultSet mijnResultset = Database.voerSqlUitEnHaalResultaatOp("SELECT e.Id, e.Nummer, e.Actief, z.Zonenummer from tblExperimenten e inner join tblZones z on e.Zone_Id = z.Id where e.Id = ?", new Object[] { id });
			if (mijnResultset != null) {
				mijnResultset.first();
				resultaat = converteerHuidigeRijNaarObject(mijnResultset);
                                resultaat = getTextById(id, resultaat);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}

		return resultaat;
	}
        
    public static ArrayList<Experiment> getExperimentByNumber(int nummer) {
		ArrayList<Experiment> resultaat = new ArrayList<Experiment>();
                Experiment exp = null;
		try {
			ResultSet mijnResultset = Database.voerSqlUitEnHaalResultaatOp("SELECT e.Id, e.Nummer, e.Actief, z.Zonenummer from tblExperimenten e inner join tblZones z on e.Zone_Id = z.Id where e.nummer = ?", new Object[] { nummer });
			if (mijnResultset != null) {
				while (mijnResultset.next()) {
                                    exp = converteerHuidigeRijNaarObject(mijnResultset);
                                    exp = getTextById(exp.getId(), exp);
                                    resultaat.add(exp);
                                }
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
                
		return resultaat;
	}
                
    public static ArrayList<Experiment> getExperimentByName(String naam) {
		ArrayList<Experiment> resultaat = new ArrayList<Experiment>();
                Experiment exp = null;
		try {
			ResultSet mijnResultset = Database.voerSqlUitEnHaalResultaatOp("SELECT Exp_Id FROM tblTeksten WHERE Tekst LIKE '%" + naam + "%' AND Type_Id = ? AND Taal_Id = ?", new Object[] { 1, 1 });
			if (mijnResultset != null) {
				while (mijnResultset.next()) {
                                    exp = getExperimentById(mijnResultset.getInt("Exp_Id"));
                                    exp = getTextById(exp.getId(), exp);
                                    resultaat.add(exp);
                                }
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
                
		return resultaat;
	}
        
    public static Experiment getTextById(int id, Experiment resultaat) {
		try {
			ResultSet mijnResultset = Database.voerSqlUitEnHaalResultaatOp("SELECT * from tblTeksten where Exp_Id = ?", new Object[] { id });
			if (mijnResultset != null) {
				while (mijnResultset.next()) {
                                    //resultaat = converteerHuidigeRijNaarObject(mijnResultset);
                                    
                                    int taal = mijnResultset.getInt("Taal_Id")-1;
                                    int soortTekst = mijnResultset.getInt("Type_Id")-1;
                                    String tekst = mijnResultset.getString("Tekst");
                                    
                                    if (taal >= 0 && soortTekst >= 0){
                                      resultaat.teksten[taal][soortTekst] = tekst;
                                    }   
                                }
                        }
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}

		return resultaat;
	}
        
    public static Experiment voegExperimentToe(Experiment exp) {
            try {
                    int zoneId = ZoneDao.getIdVanZoneOpNummer(exp.getZone());
                    Database.voerSqlUitEnHaalAantalAangepasteRijenOp("INSERT INTO tblExperimenten (Nummer, Actief, Zone_ID) VALUES (?,?,?)", new Object[] { exp.getNummer(), exp.getActief(), zoneId});
                    int ExpId = getExperimentIdByData(exp.getNummer(), zoneId, exp.getActief());
                    exp.setId(ExpId);
                    setTextById(exp);
            } 
            catch (Exception ex) {
                    ex.printStackTrace();
                    // Foutafhandeling naar keuze
            }
            return exp;
    }
        
    public static int getIdVanExperiment(int nummer){
            int id = 0;
            
		try {
                    ResultSet mijnResultset = Database.voerSqlUitEnHaalResultaatOp("SELECT Id from tblExperimenten where nummer = ? and ", new Object[] { nummer });
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
    
    public static int getExperimentIdByData(int nummer, int zone_Id, int actief) {
		int id = 88;
		try {
			ResultSet mijnResultset;
                        mijnResultset = Database.voerSqlUitEnHaalResultaatOp("SELECT * FROM tblExperimenten WHERE Nummer = ? AND Zone_Id = ? AND Actief = ?", new Object[] { nummer, zone_Id, actief });
			if (mijnResultset != null) {
				mijnResultset.last();
				id = mijnResultset.getInt("Id");
                                //be.ehb.database.swing.GUI.log(mijnResultset.getInt("Id"));
			}
		} catch (SQLException ex) {
			//ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
		return id;
	}
      
    public static boolean updateExperiment(Experiment exp) {
		boolean gelukt = false;
		try {
                        gelukt = true;
                        int zoneId = ZoneDao.getIdVanZoneOpNummer(exp.getZone());
                        Database.voerSqlUitEnHaalAantalAangepasteRijenOp("UPDATE tblExperimenten SET Nummer = ?, Actief = ?, Zone_Id = ? WHERE Id = ?", new Object[] { exp.getNummer(), exp.getActief(), zoneId, exp.getId()});
                        for (int teller1 = 0; teller1 < 3; teller1++){
                            for (int teller2 = 0; teller2 < 4; teller2++){
                                Database.voerSqlUitEnHaalAantalAangepasteRijenOp("UPDATE tblTeksten SET Tekst = ? WHERE Exp_Id = ? AND Taal_Id = ? AND Type_Id = ?", new Object[] { exp.teksten[teller1][teller2], exp.getId(), teller1 + 1, teller2 + 1 });
                            }
                        }
                } catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
                return gelukt;
	}
    
    public static void setTextById(Experiment exp) {
		try {              
                        for (int teller1 = 0; teller1 < 3; teller1++){
                            for (int teller2 = 0; teller2 < 4; teller2++){
                                Database.voerSqlUitEnHaalAantalAangepasteRijenOp("INSERT INTO tblTeksten (Exp_Id, Taal_Id, Type_Id, Tekst) VALUES (?,?,?,?)", new Object[] { exp.getId(), teller1 + 1, teller2 + 1, exp.teksten[teller1][teller2] });
                            }
                        }
                } catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
    }
   
    public static int verwijderExperiment(int Id) {
		int aantalAangepasteRijen = 0;
		try {
                        Database.voerSqlUitEnHaalAantalAangepasteRijenOp("DELETE FROM tblVideoExp WHERE Exp_Id = ?", new Object[] { Id });
                        Database.voerSqlUitEnHaalAantalAangepasteRijenOp("DELETE FROM tblFotoExp  WHERE Exp_Id = ?", new Object[] { Id });
                        Database.voerSqlUitEnHaalAantalAangepasteRijenOp("DELETE FROM tblTeksten  WHERE Exp_Id = ?", new Object[] { Id });
			aantalAangepasteRijen = Database.voerSqlUitEnHaalAantalAangepasteRijenOp("DELETE FROM tblExperimenten WHERE Id = ?", new Object[] { Id });

		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
		return aantalAangepasteRijen;
	}
    
    private static Experiment converteerHuidigeRijNaarObject(ResultSet mijnResultset) throws SQLException {
	return new Experiment(mijnResultset.getInt("id"), mijnResultset.getInt("nummer"), mijnResultset.getInt("actief"), mijnResultset.getInt("zonenummer"));
    }
}
