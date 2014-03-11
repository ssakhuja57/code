/* set revision, DESC, State, move to folder */
package com.bec.migration.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.StringTokenizer;

import wt.dataops.containermove.ContainerMoveHelper;
import wt.doc.WTDocument;
import wt.doc.WTDocumentHelper;
import wt.doc.WTDocumentMaster;
import wt.doc.WTDocumentMasterIdentity;
import wt.fc.Identified;
import wt.fc.IdentityHelper;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTObject;
import wt.fc.WTReference;
import wt.httpgw.GatewayAuthenticator;
import wt.iba.definition.StringDefinition;
import wt.iba.value.IBAHolder;
import wt.iba.value.StringValue;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.method.RemoteMethodServer;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.VersionControlHelper;
import wt.lifecycle.LifeCycleServerHelper;

import wt.vc.VersionControlHelper;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;
import wt.vc.Mastered;
import wt.vc.Versioned;
import wt.vc.Iterated;
import wt.fc.Persistable;
import wt.fc.collections.WTValuedHashMap;
import wt.fc.collections.WTValuedMap;
import wt.folder.Folder;
import wt.folder.FolderHelper;

import wt.part.WTPartMaster;
import wt.part.WTPart;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SetState {
	private static boolean VERBOSE = false;

	
	private static String SERVER_URL = "http://localhost/Windchill";
	private static String TASKS_DIR = "/servlet/IE/tasks/com/bec/util/";

	public static Mastered getMaster(Class obj_class, String number) throws WTException, ClassNotFoundException {
		if (VERBOSE) System.out.println("Searching for Master with Number " + number + "...");
	
		try{
			Field number_field = obj_class.getField("NUMBER");
			Mastered master = null;
			QuerySpec qs = new QuerySpec(obj_class);
			qs.appendSearchCondition(new SearchCondition(obj_class, number_field.getName().toLowerCase(), SearchCondition.EQUAL, number));
			final QueryResult qr = PersistenceHelper.manager.find(qs);
			if (VERBOSE) System.out.println("FOUND " + qr.size() + " with Master Number " + number + ".");
			if (qr.size() == 1) {
				while (qr.hasMoreElements()) {
					master = (Mastered)qr.nextElement();
				}
			}
			return master;
		 } catch (NoSuchFieldException e){
			 System.out.println("The field NUMBER does not exist for this object type, skipping");
			 return null;
			 }
	}
	
	public static Versioned getDetail(Mastered master, String rev, String iter) throws WTException {
	
	Versioned detail = null;
	
   QueryResult qr = VersionControlHelper.service.allIterationsOf(master);

   if (qr.size() >= 1) {
    
    do {
    
     detail = (Versioned)qr.nextElement();
          
	  if (detail.getVersionIdentifier().getValue().equals(rev) && detail.getIterationIdentifier().getValue().equals(iter))
		return detail;
	  
      }
     while(qr.hasMoreElements());

   }

	System.out.println("0 results for this number with revison " + rev + " and iteration " + iter );
   
   return null;

 }
	
	public static Versioned getLatestDetail(Mastered master) throws WTException {
		Versioned detail = null;
		QueryResult qr = VersionControlHelper.service.allIterationsOf(master);
		if (qr.size() > 0) {
			detail = (Versioned)qr.nextElement();
			System.out.println("revision: " + detail.getVersionIdentifier().getValue());
			System.out.println("iteration: " + detail.getIterationIdentifier().getValue());
		}

		return detail;
	}

/*	
	public static void setState ( WTDocument detaildoc, String state) throws Exception
	   {
		try {
			ReferenceFactory rf = new ReferenceFactory();
			String obid = rf.getReferenceString((Persistable)detaildoc);
			String url = SERVER_URL + TASKS_DIR + "SetState.xml?OBID=" + obid;
			url += "&STATE=" + state;
			RunTask.executeURL(url);		 
		} catch (Exception e) {
			System.out.println("FAILED TO SET STATE");
		}
	}
*/

	
	
	public static void main(String[] args) {
		System.out.println("Starting...");

		RemoteMethodServer rms = RemoteMethodServer.getDefault();
		GatewayAuthenticator auth = new GatewayAuthenticator();
		auth.setRemoteUser("wcadmin");
		rms.setAuthenticator(auth);
		
		System.out.println ("Number of args = " + args.length);
		if (args.length != 2){
			System.out.println("Error: Invalid number of arguments.");
			System.out.println("usage: SetState <File with path> <verbose true|false>");
			System.out.println("example SetState \"C:\\mytextfilewithnumbers.txt\" true"); 
			System.exit(1);
		}

		String filePath = args[0];
		String verbose = args[1];
		if (verbose.equals("true"))
			VERBOSE = true;
			
			
		File file = new File(filePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedReader br = null;
		int objects_processed = 0;
		
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			br = new BufferedReader(new InputStreamReader(bis));
			
			String number, revision, iteration, state;
			
			int count = 0;
			
			while (br.ready()) {
				// file content: number~revision~iteration~targetState
				//example: 278520-angle-bench.prt~A~3~RELEASED

				count = count + 1;
				String line = br.readLine();

				StringTokenizer tokenizer = new StringTokenizer(line, "~");
				
				number = tokenizer.nextToken();
				
				number = number.toUpperCase();
				if (VERBOSE) System.out.println("uppercased number: " + number);

				revision = tokenizer.nextToken();			
				iteration = tokenizer.nextToken();
				state = tokenizer.nextToken();
				
				

				if (VERBOSE) System.out.println("BEGIN: " + number);
				if (VERBOSE) System.out.println("Count: " + count);				
				if (VERBOSE) System.out.println("number: " + number);
				if (VERBOSE) System.out.println("revision: " + revision);
				if (VERBOSE) System.out.println("iteration: " + iteration);
				if (VERBOSE) System.out.println("target state: " + state);
				if (VERBOSE) System.out.println("");

				//lrl
			try {
				WTDocumentMaster master = (WTDocumentMaster)getMaster(WTDocumentMaster.class, number);
				
				if (VERBOSE) System.out.println("master: " + master.toString());
				if (master != null){
					WTDocument detail = (WTDocument)getDetail(master, revision, iteration);
					if ( WorkInProgressHelper.isCheckedOut(detail)) {
						System.out.println ("ERROR: detail is checked out, skipping - " + number);
						//System.out.println("WARNING: item is checked out, now undoing checkout");
						//WorkInProgressHelper.service.undoCheckout(detail);
					} else {
					if (detail != null){
						if (VERBOSE) System.out.println("detail: " + detail.toString());
						  try{

							   	if (VERBOSE) System.out.println("Calling setState: " + detail.toString() + "  State = " + state);
								//Chris HERE
								// Could we replace the method or put the call inline here?
								//
							   //	setState(detail, state);
								State theState  = State.toState(state);
								
//								LifeCycleServerHelper.service.setState(detail, theState);
//								LifeCycleServerHelper.service.setState((LifeCycleManaged)inst, theState);
								LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged)detail, theState);

								//wt.fc.WTObject,wt.lifecycle.State
							   objects_processed = objects_processed + 1;
							} catch (Exception wtee) {
								System.out.println("ERROR: FOUND for Number: " + number);
								wtee.printStackTrace();
						    } 
							//setState(detail, state);
						}
					}
				}
			
			} catch (Exception e) {
					System.out.println("ERROR: **** Exeception " + e.getMessage() + " number: " + number + " ****");
					System.out.println("Begin Stack Trace ****");
					e.printStackTrace();
					System.out.println("End Stack Trace ****");

					
			}
				
			if (VERBOSE) System.out.println("END: " + number);

			}
			if (VERBOSE) System.out.println("Done.");
		}
		catch (Exception wtee) {
			wtee.printStackTrace();
		}
		if (VERBOSE) System.out.println("Number of objects processed = " + objects_processed);

		System.exit(0);
	}
}
