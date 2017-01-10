package items;

public class Tool extends AbstractItem{
	
	public static final String AXE_IRON = "axe_iron";
	
	public Tool(){
		super(1);
	}
	
	@Override	
	public String getIconName(){
		return AXE_IRON;
	}
	
	
}
