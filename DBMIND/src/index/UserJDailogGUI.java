package index;

//UserJDailogGUI.java
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserJDailogGUI extends JDialog implements ActionListener{
	
	JPanel pw=new JPanel(new GridLayout(4,1));
	JPanel pc=new JPanel(new GridLayout(4,1));
	JPanel ps=new JPanel();

	JLabel lable_Name = new JLabel("�̸�");
	JLabel lable_Remain=new JLabel("�����");
	JLabel lable_Sales=new JLabel("����");
	JLabel lable_Etc=new JLabel("���");


	JTextField name=new JTextField();
	JTextField remain=new JTextField();
	JTextField sales=new JTextField();
	JTextField etc=new JTextField();
	

	JButton confirm;
	JButton reset=new JButton("���");

 MenuJTabaleExam me;

 JPanel idCkP =new JPanel(new BorderLayout());
 JButton idCkBtn = new JButton("IDCheck");
 
 UserDefaultJTableDAO dao =new UserDefaultJTableDAO();
 

	public UserJDailogGUI(MenuJTabaleExam me, String index){
		super(me,"���̾�α�");
		this.me=me;
		if(index.equals("���")){
			confirm=new JButton(index);
		}else{
			confirm=new JButton("�������");	
			
			//text�ڽ��� ���õ� ���ڵ��� ���� �ֱ�
			int row = me.jt.getSelectedRow();//���õ� ��
			name.setText( me.jt.getValueAt(row, 0).toString() );
			remain.setText( me.jt.getValueAt(row, 1).toString() );
			sales.setText( me.jt.getValueAt(row, 2).toString() );
			etc.setText( me.jt.getValueAt(row, 3).toString() );
			
			//id text�ڽ� ��Ȱ��
			name.setEditable(false);
	
			//IDCheck��ư ��Ȱ��ȭ
			idCkBtn.setEnabled(false);
		}
		
		
		//Label�߰��κ�
		pw.add(lable_Name);//ǰ��
		pw.add(lable_Remain);//���
		pw.add(lable_Sales);//����
		pw.add(lable_Etc);//���
	
		
		idCkP.add(name,"Center");
		idCkP.add(idCkBtn,"East");
		
		//TextField �߰�
		pc.add(idCkP);
		pc.add(remain);
		pc.add(sales);
		pc.add(etc);
		
		
		
		ps.add(confirm); 
		ps.add(reset);
	
		add(pw,"West"); 
		add(pc,"Center");
		add(ps,"South");
		
		setSize(300,250);
		setVisible(true);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		//�̺�Ʈ���
      confirm.addActionListener(this); //����/���� �̺�Ʈ���
      reset.addActionListener(this); //��� �̺�Ʈ���
      idCkBtn.addActionListener(this);// ID�ߺ�üũ �̺�Ʈ ���
		
	}//�����ڳ�
  
	/**
	 * ����/����/���� ��ɿ� ���� �κ�
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {
	   String btnLabel =e.getActionCommand();//�̺�Ʈ��ü ���� Label ��������
	    
	   if(btnLabel.equals("���")){
		   if(dao.userListInsert(this) > 0 ){//��ǰ���
			   messageBox(this , name.getText()+"��ǰ��� �Ǿ����ϴ�.");
			   dispose();//JDialog â�ݱ�
			   
			   dao.userSelectAll(me.dt);//��緹�ڵ尡���ͼ� DefaultTableModel�� �ø���
			   
			   if(me.dt.getRowCount() > 0)
				   me.jt.setRowSelectionInterval(0, 0);//ù��° �� ����
			   
		   }else{//���Խ���
			   messageBox(this,"��ǰ��� ���� �ʾҽ��ϴ�.");
		   }
		   
	   }else if(btnLabel.equals("�������")){
		   
		    if( dao.userUpdate(this) > 0){
		    	messageBox(this, "�����Ϸ�Ǿ����ϴ�.");
		    	dispose();
		    	dao.userSelectAll(me.dt);
		    	if(me.dt.getRowCount() > 0 ) me.jt.setRowSelectionInterval(0, 0);
		    	
		    }else{
		    	messageBox(this, "�������� �ʾҽ��ϴ�.");
		    }
		   
		   
		   
	   }else if(btnLabel.equals("���")){
		   dispose();
		   
	   }else if(btnLabel.equals("IDCheck")){
		   //id�ؽ�Ʈ�ڽ��� �� ������ �޼��� ��� ������ DB�����Ѵ�.
		   if(name.getText().trim().equals("")){
			   messageBox(this,"��ǰ���� �Է����ּ���.");
			   name.requestFocus();//��Ŀ���̵�
		   }else{
			   
			  if(  dao.getIdByCheck(name.getText()) ){ //�ߺ��ƴϴ�.(��밡��) 
				  messageBox(this, name.getText()+"�� ��ϰ����մϴ�.");  
			  }else{ //�ߺ��̴�.
				  messageBox(this,name.getText()+"�� �ߺ��Դϴ�.");
				  
				  name.setText("");//text�ڽ������
				  name.requestFocus();//Ŀ������
			  }
			   
		   }
		   
	   }
	   
		
	}//actionPerformed��
	
	/**
	 * �޽����ڽ� ����ִ� �޼ҵ� �ۼ�
	 * */
	public static void messageBox(Object obj , String message){
		JOptionPane.showMessageDialog( (Component)obj , message);
	}

}//Ŭ������