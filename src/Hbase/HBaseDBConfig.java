package Hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;

public class HBaseDBConfig {

	static Configuration hc = HBaseConfiguration.create();
	public static Map<String, HTable> htable = new HashMap<>();

	public static void setHtable(String htable) {
		try {
			HBaseDBConfig.htable.put(htable, new HTable(hc, htable));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create table
	 * 
	 * @param name
	 * @param family
	 */
	public static void configTable(String name, String... family) {
		List<String> ls = new ArrayList<String>();
		for (String string : family) {
			ls.add(string);
		}
		if (!htable.containsKey(name)) {
			createTableIfNotExiste(name, ls);
		}
	}

	public static void createTableIfNotExiste(String name, List<String> family) {
		HTableDescriptor ht = null;

		try {
			HBaseAdmin hba = new HBaseAdmin(hc);
			for (HTableDescriptor exist : hba.listTables()) {
				if (exist.getNameAsString().equals(name)) {
					ht = exist;
					for (String fam : family) {

						// verfiy if the family exist 
						boolean columnexist = false;
						for (HColumnDescriptor exicolomn : ht.getFamilies()) {
							if (exicolomn.getNameAsString().equals(fam)) {
								columnexist = true;
								break;
							}
						}

						// insert if it does exist
						if (!columnexist) {
							HColumnDescriptor columnDescriptor = new HColumnDescriptor(
									fam);
							ht.addFamily(columnDescriptor);
							hba.addColumn(name.getBytes(), columnDescriptor);
						}
					}
					setHtable(name);
				}
			}

			if (ht == null) {
				ht = new HTableDescriptor(TableName.valueOf(name));
				for (String fam : family) {
					ht.addFamily(new HColumnDescriptor(fam));
				}
				hba.createTable(ht);
				setHtable(name);
			}

		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Inserer une ligne
	 * 
	 * @param table
	 * @param raw
	 * @param family
	 * @param qualifier
	 * @param value
	 */
	public static synchronized void putRaw(String tablename, String raw,
			String family, String qualifier, String value) {

		if (!htable.containsKey(tablename)) {
			try {
				throw new NoTableConfigured();
			} catch (NoTableConfigured e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			return;
		}
		System.err.println(htable + " " + raw + " " + family + " " + qualifier
				+ " " + value);
		Put row = new Put(new String(raw).getBytes());
		row.add(new String(family).getBytes(),
				new String(qualifier).getBytes(), new String(value).getBytes());

		try {
			htable.get(tablename).put(row);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Result getRaw(String tablename, String raw) {

		if (!htable.containsKey(tablename)) {
			try {
				throw new NoTableConfigured();
			} catch (NoTableConfigured e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		System.out.println(" HBASE : get Raw " + raw);
		Get get = new Get(raw.getBytes());
		try {
			System.out.println(" HBASE : get Raw GET " + get.toJSON());
			return htable.get(tablename).get(get);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
