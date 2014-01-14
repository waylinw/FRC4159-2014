package org.team4159.support;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.io.Connector;
import com.sun.cldc.jna.Function;
import com.sun.cldc.jna.NativeLibrary;
import com.sun.cldc.jna.Pointer;
import com.sun.squawk.microedition.io.FileConnection;

public class DataCollector
{
	private Vector names = new Vector ();
	private Vector values = new Vector ();
	private Hashtable indices = new Hashtable ();
	private final String path;
	
	private boolean failed = false;
	private PrintStream stream;
	
	public DataCollector (String path)
	{
		this.path = path;
	}
	
	public void addColumn (String key, String name)
	{
		int n = names.size ();
		names.addElement (name);
		values.addElement (null);
		indices.put (key, new Integer (n));
	}
	
	public void setRowValue (String key, long n)
	{
		setRowValue (key, new Long (n));
	}
	
	public void setRowValue (String key, double n)
	{
		setRowValue (key, new Double (n));
	}
	
	public void setRowValue (String key, Object value)
	{
		Integer nn = (Integer) indices.get (key);
		if (nn == null)
			return;
		
		int n = nn.intValue ();
		values.setElementAt (value, n);
	}
	
	public void writeRow ()
	{
		if (failed)
			return;
		try {
			writeRowImpl ();
		} catch (Throwable t) {
			System.err.println ("Error writing row to " + path + ":");
			t.printStackTrace ();
			failed = true;
		}
	}
	
	private void writeRowImpl () throws Throwable
	{
		if (stream == null)
		{
			// open stream
			openStreamImpl ();
			
			// write headers
			for (int i = 0, ii = names.size (); i < ii; i++)
			{
				if (i != 0)
					stream.print (",");
				stream.print (names.elementAt (i));
			}
			
			stream.println ();
		}
		
		for (int i = 0, ii = values.size (); i < ii; i++)
		{
			if (i != 0)
				stream.print (',');
			
			Object obj = values.elementAt (i); 
			stream.print (obj != null ? obj : "");
			values.setElementAt (null, i); // clear object
		}
		
		stream.println ();
	}
	
	private void openStreamImpl () throws Throwable
	{
		int next = advanceNextImpl ();
		
		String url = "file:///" + path + "-" + padNumber (next) + ".csv";
		FileConnection fc = (FileConnection) Connector.open (url, Connector.READ_WRITE);
		
		fc.create ();
		stream = new PrintStream (fc.openOutputStream ());
	}
	
	private int advanceNextImpl () throws Throwable
	{
		int next = 1;
		
		int slashEnd = path.lastIndexOf ('/') + 1;
		String dirPrefix = path.substring (0, slashEnd);
		
		String url = "file:///" + dirPrefix + "DataCollectorNext.bin";
		FileConnection fc = (FileConnection) Connector.open (url, Connector.READ_WRITE);
		
		// read next number
		if (fc.exists ())
		{
			DataInputStream dis = fc.openDataInputStream ();
			try { next = dis.readInt (); } catch (EOFException e) {}
			dis.close ();
		}
		
		// write back number
		{
			try {
				fc.create ();
			} catch (IOException e) {
				Function mkdir = NativeLibrary.getDefaultInstance ().getFunction ("mkdir");
				
				Pointer ptr = Pointer.createStringBuffer (dirPrefix);
				int res = mkdir.call2 (ptr, 0666);
				ptr.free ();
				
				if (res == 0)
					fc.create ();
				else
					throw e;
			}
			
			DataOutputStream dos = fc.openDataOutputStream ();
			dos.writeInt (next + 1);
			dos.close ();
		}
		
		fc.close ();
		
		return next;
	}
	
	private String padNumber (int x)
	{
		if (x < 0)
			return "" + x;
		else if (x < 10)
			return "00000" + x;
		else if (x < 100)
			return "0000" + x;
		else if (x < 1000)
			return "000" + x;
		else if (x < 10000)
			return "00" + x;
		else if (x < 100000)
			return "0" + x;
		else
			return "" + x;
	}
}