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
			ResultSet mijnResultset = Database.voerSqlUitEnHaalResultaatOp("SELECT * from tblExperimenten");
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
			ResultSet mijnResultset = Database.voerSqlUitEnHaalResultaatOp("SELECT * from tblExperimenten where ExperimentId = ?", new Object[] { id });
			if (mijnResultset != null) {
				mijnResultset.first();
				resultaat = converteerHuidigeRijNaarObject(mijnResultset);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}

		return resultaat;
	}
/*
	public static int voegExperimentToe(Experiment nieuweExperiment) {
		int aantalAangepasteRijen = 0;
		try {
			aantalAangepasteRijen = Database.voerSqlUitEnHaalAantalAangepasteRijenOp("INSERT INTO Experiment (Voornaam, Achternaam, Adres) VALUES (?,?,?)", new Object[] { nieuweExperiment.getVoornaam(), nieuweExperiment.getAchternaam(), nieuweExperiment.getAdres() });
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
		return aantalAangepasteRijen;
	}

	public static int updateExperiment(Experiment nieuweExperiment) {
		int aantalAangepasteRijen = 0;
		try {
			aantalAangepasteRijen = Database.voerSqlUitEnHaalAantalAangepasteRijenOp("UPDATE Experiment SET Voornaam = ?, Achternaam = ?, Adres = ? WHERE ExperimentId = ?", new Object[] { nieuweExperiment.getVoornaam(), nieuweExperiment.getAchternaam(), nieuweExperiment.getAdres(), nieuweExperiment.getExperimentId() });
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
		return aantalAangepasteRijen;
	}

	public static int verwijderExperiment(int ExperimentId) {
		int aantalAangepasteRijen = 0;
		try {
			aantalAangepasteRijen = Database.voerSqlUitEnHaalAantalAangepasteRijenOp("DELETE FROM Experiment WHERE ExperimentId = ?", new Object[] { ExperimentId });
		} catch (SQLException ex) {
			ex.printStackTrace();
			// Foutafhandeling naar keuze
		}
		return aantalAangepasteRijen;
	}
*/
	private static Experiment converteerHuidigeRijNaarObject(ResultSet mijnResultset) throws SQLException {
		return new Experiment(mijnResultset.getInt("id"), mijnResultset.getInt("nummer"), mijnResultset.getInt("actief"), mijnResultset.getInt("zone"));
	}
}
