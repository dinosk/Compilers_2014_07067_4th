//
// Generated by JTB 1.3.2 DIT@UoA patched
//

package visitor;
import syntaxtree.*;
import java.util.*;
import java.io.BufferedWriter;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class SpigletVisitor implements GJVisitor<String,String> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //

  HashMap<String, Integer> tempCount;
  TempFactory tempFactory;
  String code;
  ArrayList<String> callParams;
  BufferedWriter out;

  public SpigletVisitor( HashMap<String, Integer> tempCount, BufferedWriter out){
    this.tempCount = tempCount;
    this.tempFactory = new TempFactory();
    this.code = "";
    this.callParams = new ArrayList<String>();
    this.out = out;
  }

  public void emit(String code){
    this.code += code;
  }

   public String visit(NodeList n, String argu) {
      if (n.size() == 1)
         return n.elementAt(0).accept(this,argu);
      String _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public String visit(NodeListOptional n, String argu) {
      if ( n.present() ) {
         if (n.size() == 1)
            return n.elementAt(0).accept(this,argu);
         String _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public String visit(NodeOptional n, String argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }

   public String visit(NodeSequence n, String argu) {
      if (n.size() == 1)
         return n.elementAt(0).accept(this,argu);
      String _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public String visit(NodeToken n, String argu) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> n1="MAIN"
    * f1 -> n2=StmtList()
    * f2 -> n4="END"
    * f3 -> ( n6=Procedure() )*
    * f4 -> n8=<EOF>
    */
   public String visit(Goal n, String argu) {
      String _ret=null;
      n.f0.accept(this, argu);
      emit("\tMAIN\n");
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      emit("\tEND\n\n");
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      // System.out.println("\n\nFinal Codes: \n\n\n"+this.code);
      try {
        out.write(code);
        out.close();
      } catch( Exception ex ) {
        System.out.println( "Could not write to output file" );
      }
      System.out.println("Program translated successfully.");
      return _ret;
   }

   /**
    * f0 -> (  ( n3=Label() )? n4=Stmt() )*
    */
   public String visit(StmtList n, String argu) {
    // System.out.println("StmtList");
      String stmtList = n.f0.accept(this, "Label");   // pername ws orisma to Label wste na ektypwthei to (Label() ?) an yparxei
      return stmtList;
   }

   /**
    * f0 -> n0=Label()
    * f1 -> n2="["
    * f2 -> n3=IntegerLiteral()
    * f3 -> n5="]"
    * f4 -> n6=StmtExp()
    */
   public String visit(Procedure n, String argu) {
    // System.out.println("Procedure");
      String _ret=null;

      String name = n.f0.accept(this, argu);
      emit(name+"\t[");      
      tempFactory.setCounter(tempCount.get(name)+1);

      n.f1.accept(this, argu);
      String argNo = n.f2.accept(this, null);
      emit(argNo+"]\n");
      n.f3.accept(this, argu);
      emit("BEGIN\n");
      n.f4.accept(this, "Procedure"); // pername ws orisma to Procedure gia na ektypwthei to RETURN sto StmtExp
      emit("END\n\n");
      return _ret;
   }

   /**
    * f0 -> ( n1=NoOpStmt() | n2=ErrorStmt() | n3=CJumpStmt() | n4=JumpStmt() | n5=HStoreStmt() | n6=HLoadStmt() | n7=MoveStmt() | n8=PrintStmt() )
    */
   public String visit(Stmt n, String argu) {
    // System.out.println("Stmt");
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> n1="NOOP"
    */
   public String visit(NoOpStmt n, String argu) {
    // System.out.println("NoOpStmt");
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> n1="ERROR"
    */
   public String visit(ErrorStmt n, String argu) {
    // System.out.println("ErrorStmt");
      emit("\tERROR\n");
      return n.f0.accept(this, argu);
   }

   /**
    * f0 -> n1="CJUMP"
    * f1 -> n2=Exp()
    * f2 -> n3=Label()
    */
   public String visit(CJumpStmt n, String argu) {
    // System.out.println("CJumpStmt");
      String _ret=null;
      n.f0.accept(this, argu);
      String returnTemp = n.f1.accept(this, "returnTemp");
      String label = n.f2.accept(this, null);
      emit("\tCJUMP "+returnTemp+" "+label+"\n");
      return _ret;
   }

   /**
    * f0 -> n1="JUMP"
    * f1 -> n2=Label()
    */
   public String visit(JumpStmt n, String argu) {
    // System.out.println("JumpStmt");
      String _ret=null;
      n.f0.accept(this, argu);
      String label = n.f1.accept(this, null);
      emit("\tJUMP "+label+"\n");
      return _ret;
   }

   /**
    * f0 -> n1="HSTORE"
    * f1 -> n2=Exp()
    * f2 -> n3=IntegerLiteral()
    * f3 -> n4=Exp()
    */
   public String visit(HStoreStmt n, String argu) {
    // System.out.println("HStoreStmt");
      String _ret=null;
      n.f0.accept(this, argu);
      String resultTemp = n.f1.accept(this, "returnTemp");  // ta orismata returnTemp aplopoioun opoia exp kai epistrefoun to teliko TEMP me to apotelesma
      String intLiteral = n.f2.accept(this, null);
      String resultTemp2 = n.f3.accept(this, "HSTORE");
      emit("\tHSTORE "+resultTemp+" "+intLiteral+" "+resultTemp2+"\n");
      return _ret;
   }

   /**
    * f0 -> n1="HLOAD"
    * f1 -> n2=Temp()
    * f2 -> n3=Exp()
    * f3 -> n4=IntegerLiteral()
    */
   public String visit(HLoadStmt n, String argu) {
    // System.out.println("HLoadStmt");
      String _ret=null;
      n.f0.accept(this, argu);
      String temp = n.f1.accept(this, "returnTemp");
      String resultTemp = n.f2.accept(this, "returnTemp");
      String intLiteral = n.f3.accept(this, null);
      emit("\tHLOAD "+temp+" "+resultTemp+" "+intLiteral+"\n");
      return _ret;
   }

   /**
    * f0 -> n1="MOVE"
    * f1 -> n2=Temp()
    * f2 -> n3=Exp()
    */
   public String visit(MoveStmt n, String argu) {
    // System.out.println("MoveStmt with argu: "+argu);
      String _ret=null;
      n.f0.accept(this, argu);
      String temp = n.f1.accept(this, "returnTemp");
      String resultTemp = n.f2.accept(this, "returnTemp");
      emit("\tMOVE "+temp+" "+resultTemp+"\n");
      return _ret;
   }

   /**
    * f0 -> n1="PRINT"
    * f1 -> n2=Exp()
    */
   public String visit(PrintStmt n, String argu) {
    // System.out.println("PrintStmt");
      String _ret=null;
      n.f0.accept(this, argu);
      String resultTemp = n.f1.accept(this, "returnTemp");
      emit("\tPRINT "+resultTemp+"\n");
      return _ret;
   }

   /**
    * f0 -> ( n1=StmtExp() | n2=Call() | n3=HAllocate() | n4=BinOp() | n5=Temp() | n6=IntegerLiteral() | n7=Label() )
    */
   public String visit(Exp n, String argu) {
    // System.out.println("Exp with argu: "+argu);
      String expResult = n.f0.accept(this, argu);

      if(argu != null)
        if(argu.equals("callParamList")){
          if(expResult != null)
            callParams.add(expResult);
        }
      return expResult;
   }

   /**
    * f0 -> n1="BEGIN"
    * f1 -> n2=StmtList()
    * f2 -> n4="RETURN"
    * f3 -> n5=Exp()
    * f4 -> n7="END"
    */
   public String visit(StmtExp n, String argu) {
    // System.out.println("StmtExp");
      String _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      String resultTemp = n.f3.accept(this, "returnTemp");
      if(argu.equals("Procedure"))
        emit("\tRETURN "+resultTemp+"\n");
      n.f4.accept(this, argu);
      return resultTemp;
   }

   /**
    * f0 -> n1="CALL"
    * f1 -> n2=Exp()
    * f2 -> n4="("
    * f3 -> ( n6=Exp() )*
    * f4 -> n8=")"
    */
   public String visit(Call n, String argu) {
    // System.out.println("Call");
      String _ret=null;
      String temp = tempFactory.newTemp();
      String rawParameters = "";
      
      n.f0.accept(this, argu);
      String resultTemp = n.f1.accept(this, "returnTemp");
      n.f2.accept(this, argu);
      n.f3.accept(this, "callParamList"); // tis parametrous ja ta CALL ta vazw se ena ArrayList ston visitor

      for(String param : callParams){
        rawParameters+=param+" ";
      }
      rawParameters=rawParameters.substring(0, rawParameters.length()-1);

      n.f4.accept(this, argu);
      callParams.clear(); 
      emit("\tMOVE "+temp+" CALL "+resultTemp+" ("+rawParameters+")\n");
      return temp;
   }

   /**
    * f0 -> n1="HALLOCATE"
    * f1 -> n2=Exp()
    */
   public String visit(HAllocate n, String argu) {
    // System.out.println("HAllocate");
      String _ret=null;
      n.f0.accept(this, argu);
      String resultTemp = n.f1.accept(this, "HALLOCATE"); // to argu HALLOCATE leei sto integerLiteral na ektypwthei ws exei xwris perasma se TEMP
      return "HALLOCATE "+resultTemp;
   }

   /**
    * f0 -> n0=Operator()
    * f1 -> n1=Exp()
    * f2 -> n2=Exp()
    */
   public String visit(BinOp n, String argu) {
    // System.out.println("BinOp");
      String _ret=null;
      String temp = tempFactory.newTemp();
      String operator = n.f0.accept(this, argu);
      String lop = n.f1.accept(this, "returnTemp");
      String rop = n.f2.accept(this, "temped"); // to argu temped pernaei opoio integerLiteral diavastei se TEMP kai to epistrefei
      emit("\tMOVE "+temp+" "+operator+" "+lop+" "+rop+"\n");
      return temp;
   }

   /**
    * f0 -> ( n2="LT" | n4="PLUS" | n6="MINUS" | n8="TIMES" )
    */
   public String visit(Operator n, String argu) {
    // System.out.println("Operator");
      n.f0.accept(this, argu);
      return n.f0.choice.toString();
   }

   /**
    * f0 -> n1="TEMP"
    * f1 -> n2=IntegerLiteral()
    */
   public String visit(Temp n, String argu) {
    // System.out.println("Temp");
      String _ret=null;

      n.f0.accept(this, argu);
      String tempNo = n.f1.accept(this, null);
      if(argu != null){
        if(argu.equals("returnTemp") || argu.equals("HSTORE") || argu.equals("HALLOCATE")){
          return "TEMP "+tempNo;       
        }
        else if(argu.equals("callParamList")){
          callParams.add("TEMP "+tempNo);
        }
      }
      return _ret;
   }

   /**
    * f0 -> n1=<INTEGER_LITERAL>
    */
   public String visit(IntegerLiteral n, String argu) {
    // System.out.println("IntegerLiteral with argu = "+argu);
    String temp = tempFactory.newTemp();
    n.f0.accept(this, argu);

    if(argu!=null){
      if(argu.equals("temped") || argu.equals("returnTemp") || argu.equals("HSTORE")){
        emit("\tMOVE "+temp+" "+n.f0.toString()+"\n");
        return temp;
      }
      else if(argu.equals("callParamList")){
        emit("\tMOVE "+temp+" "+n.f0.toString()+"\n");
        callParams.add(temp);
        return null;
      }
    }
    return n.f0.toString();
   }

   /**
    * f0 -> n1=<IDENTIFIER>
    */
   public String visit(Label n, String argu) {
    // System.out.println("Label");
      n.f0.accept(this, argu);
      if(argu != null){
        if(argu.equals("HSTORE")){
          String temp = tempFactory.newTemp();
          emit("\tMOVE "+temp+" "+n.f0.toString()+"\n");
          return temp;
        }
        else if(argu.equals("Label")){
          emit(n.f0.toString()+"\tNOOP\n");
        }
      }
      return n.f0.toString();
   }

}