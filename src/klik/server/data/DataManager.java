package klik.server.data;

import java.sql.SQLException;
import java.util.List;

import klik.shared.constants.X10.State;
import klik.shared.constants.X10.Type;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DataManager {

	private static JdbcPooledConnectionSource connectionSource;
	private static Dao<X10Unit, Integer> x10UnitDao;

	static {
		try {
			connectionSource = new JdbcPooledConnectionSource("jdbc:sqlite::memory:");
			connectionSource.setMaxConnectionAgeMillis(Long.MAX_VALUE);
			connectionSource.setCheckConnectionsEveryMillis(60 * 1000);
			connectionSource.setTestBeforeGet(true);
			TableUtils.createTable(connectionSource, X10Unit.class);
			x10UnitDao = DaoManager.createDao(connectionSource, X10Unit.class);
			addTestUnits();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addTestUnits() {
		X10Unit unit = new X10Unit();
		unit.setAddress("A1");
		unit.setName("Wall lamp");
		unit.setState(State.OFF);
		unit.setType(Type.DIMMABLE_LIGHT);
		unit.setValue(0);
		try {
			x10UnitDao.create(unit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<X10Unit> getUnits() throws SQLException {
		try {
			return x10UnitDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<X10Unit> getUnitsWithAddress(String address) {
		try {
			return x10UnitDao.queryForEq("address", address);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<X10Unit> getUnitsWithHouseCode(char houseCode) {
		try {
			return x10UnitDao.queryForEq("housecode", houseCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static X10Unit getUnit(Integer id) {
		try {
			return x10UnitDao.queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void updateUnit(X10Unit unit) {
		try {
			x10UnitDao.update(unit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
