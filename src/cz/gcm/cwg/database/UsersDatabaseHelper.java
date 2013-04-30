package cz.gcm.cwg.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import cz.gcm.cwg.database.items.Users;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class UsersDatabaseHelper extends BaseDatabaseHelper {
	// the DAO object we use to access the SimpleData table
	private Dao<Users, Integer> usersDao = null;

	public UsersDatabaseHelper(Context context) {
		super(context);
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(UsersDatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTableIfNotExists(connectionSource, Users.class);
			
			long millis = System.currentTimeMillis();
			/*
			// here we try inserting data in the on-create as a test
			Dao<Users, Integer> dao = getUsersDao();
			
			// create some entries in the onCreate
			Users simple = new Users(millis);
			dao.create(simple);
			simple = new Users(millis + 1);
			dao.create(simple);
			*/
			Log.i(UsersDatabaseHelper.class.getName(), "created new entries in onCreate: " + millis);
		} catch (SQLException e) {
			Log.e(UsersDatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(UsersDatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Users.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(UsersDatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
	 * value.
	 */
	public Dao<Users, Integer> getUsersDao() throws SQLException {
		if (usersDao == null) {
			usersDao = getDao(Users.class);
		}
		return usersDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		usersDao = null;
	}
}
