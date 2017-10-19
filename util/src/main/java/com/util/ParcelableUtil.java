package com.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sgrape on 2017/5/18.
 * e-mail: sgrape1153@gmail.com
 */

public class ParcelableUtil<T extends Parcelable> implements Parcelable {
    public ParcelableUtil() {
    }

    protected ParcelableUtil(Parcel in) {
        try {
            read(this, in);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    public static final Creator<ParcelableUtil> CREATOR = new Creator<ParcelableUtil>() {
        @Override
        public ParcelableUtil createFromParcel(Parcel in) {
            return new ParcelableUtil(in);
        }

        @Override
        public ParcelableUtil[] newArray(int size) {
            return new ParcelableUtil[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        try {
            write(this, dest, flags);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void read(Object obj, Parcel p) throws IllegalAccessException, NoSuchMethodException {
        Class clz = obj.getClass();
        Field[] fields = clz.getDeclaredFields();
        Object val = null;
        for (Field f : fields) {
            val = null;
            f.setAccessible(true);
            if (f.getName().equals("CREATOR") || "CREATOR" == f.getName()) {
                continue;
            }
            final Class<?> ft = f.getType();
//            System.out.println("read ====>\t" + clz.getSimpleName() + "." + f.getName() + "\t\t type ===>  " + ft.getSimpleName());
            if (ft == Parcelable.class) {
                val = p.readParcelable(ft.getClassLoader());
            } else if (ft == Integer.class) {
                val = p.readInt();
            } else if (ft == Double.class) {
                val = p.readDouble();
            } else if (ft == Float.class) {
                val = p.readFloat();
            } else if (ft == String.class) {
                val = p.readString();
            } else if (ft == ArrayList.class || ft == List.class) {
                final Method m = ft.getMethod("get", int.class);
                Class<?> type = m.getReturnType();
                System.out.println(type);
                val = new ArrayList();
                p.readList((List) val, Thread.currentThread().getContextClassLoader());
            }
            if (val != null) f.set(obj, val);
        }
    }

    public static void write(Object obj, Parcel p, int flags) throws IllegalAccessException {
        Class clz = obj.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.getName().equals("CREATOR") || "CREATOR" == f.getName()) {
                continue;
            }
            final Class<?> ft = f.getType();
            final Object val = f.get(obj);
            if (ft == Parcelable.class) {
                p.writeParcelable((Parcelable) val, flags);
            } else if (ft == Integer.class) {
                p.writeInt((Integer) val);
            } else if (ft == Double.class) {
                p.writeDouble((Double) val);
            } else if (ft == Float.class) {
                p.writeFloat((Float) val);
            } else if (ft == String.class) {
                p.writeString((String) val);
            } else if (ft == ArrayList.class || ft == List.class) {
                p.writeList((List) f.get(obj));
            }
        }
    }
}
