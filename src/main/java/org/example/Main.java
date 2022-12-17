package org.example;

import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;

import java.nio.ByteBuffer;

public class Main {

    public static void main(String[] args) {

        System.out.println("------------------");

        System.out.println("Photovoltaik-Leistung in Watt: " + readInt32(40068));
        System.out.println("Batterie-Leistung in Watt (negative Werte = Entladung): " + readInt32(40070));
        System.out.println("Hausverbrauchs-Leistung in Watt: " + readInt32(40072));
        System.out.println("Leistung am Netzübergabepunkt in Watt: " + readInt32(40074));
        System.out.println("SOC in %: " + readInt16(40083));
        System.out.println("Autarkie %: " + readInt8(40082,0));
        System.out.println("Eigenverbrauch %: " + readInt8(40082,1));
        System.out.println("Firmware1: " + readInt8(40002,1));
        System.out.println("Firmware2: " + readInt8(40002,1));
        System.out.println("Power status: " + readInt16(40084));


        System.out.println("------------------");

    }

    private static int readInt32(int register) {

        ModbusTCPMaster master = null;

        try {
            master = new ModbusTCPMaster("192.168.2.101", 502, 5000, true);
            master.connect();

            var test22 = master.readMultipleRegisters(register -1, 2);

            var b = ByteBuffer.allocate(4);

            // word swap
            for (int i = test22.length - 1; i >= 0; i--) {
                b.put(test22[i].toBytes());
            }

            b.position(0);

            return b.getInt();

        } catch (Exception ignored) {

        } finally {
            if (master != null) {
                master.disconnect();
            }
        }

        return 0;
    }

    private static int readInt16(int register) {

        ModbusTCPMaster master = null;

        try {
            master = new ModbusTCPMaster("192.168.2.101", 502, 5000, true);
            master.connect();

            var test22 = master.readMultipleRegisters(register -1, 1);

            var b = ByteBuffer.allocate(2);


            // word swap
            for (int i = test22.length - 1; i >= 0; i--) {

                b.put(test22[i].toBytes());
            }

            b.position(0);

            return b.getShort();

        } catch (Exception ignored) {

        } finally {
            if (master != null) {
                master.disconnect();
            }
        }

        return 0;
    }

    private static int readInt8(int register, int part) {

        ModbusTCPMaster master = null;

        try {
            master = new ModbusTCPMaster("192.168.2.101", 502, 5000, true);
            master.connect();

            var test22 = master.readMultipleRegisters(register -1, 1);

            return test22[0].toBytes()[part] & 0xFF;

        } catch (Exception ignored) {

        } finally {
            if (master != null) {
                master.disconnect();
            }
        }

        return 0;
    }

}