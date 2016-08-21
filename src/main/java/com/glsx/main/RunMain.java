package com.glsx.main;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by xiaolong on 2016/7/27.
 */
public class RunMain {
    private static final Logger LOG = LoggerFactory.getLogger(RunMain.class);

    public static void main(String[] args) throws IOException {
        LOG.info("the job is beginning ...");

        File result = new File("result");
        FileWriter bw = new FileWriter(result);

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "glsx.hadoop316,glsx.hadoop317,glsx.hadoop318");

        HConnection hConnection = HConnectionManager.createConnection(conf);
        TableName tableName = TableName.valueOf("gps_recent");
        HTableInterface table = hConnection.getTable(tableName);

        Scan scan = new Scan();
        scan.setCaching(1000);
        ResultScanner scanner = table.getScanner(scan);

        String gpsTime = null;
        String latitude = null;
        String longitude = null;
        int count = 0;

        Result next;
        while ((next = scanner.next()) != null) {
            count++;
            gpsTime = Bytes.toString(next.getValue(Bytes.toBytes("info"), Bytes.toBytes("gpsTime")));
            latitude = Bytes.toString(next.getValue(Bytes.toBytes("info"), Bytes.toBytes("latitude")));
            longitude = Bytes.toString(next.getValue(Bytes.toBytes("info"), Bytes.toBytes("longitude")));
            if ((gpsTime.compareTo("2016-08-19 00:00:00") > 0) ||
                    (gpsTime.compareTo("2012-08-17 00:00:00") < 0) ||
                    (Double.valueOf(latitude) < 10) || (Double.valueOf(latitude) > 54) ||
                    (Double.valueOf(longitude) < 73) || (Double.valueOf(longitude) > 136)
                    ) {
                bw.write(Bytes.toString(next.getValue(Bytes.toBytes("info"), Bytes.toBytes("sn"))));
                bw.write("\n");
//                count++;
//                LOG.info("sn="+Bytes.toString(next.getValue(Bytes.toBytes("info"), Bytes.toBytes("sn"))));
//                LOG.info("gpsTime="+gpsTime);
//                LOG.info("latitude="+latitude);
//                LOG.info("longitude="+longitude);
            }
            if (count % 1000 == 0) {
                LOG.info("the SN "+ count + " is called...");
            }
        }

        bw.flush();
        bw.close();

        table.close();
        hConnection.close();

        LOG.info("the job is ended ...");
    }
}
