package edu.sumdu.dl.common;

/**
 * $Id: ISelfDumped.java,v 1.1.1.1 2003/10/18 13:08:21 nnick Exp $ $Log:
 * ISelfDumped.java,v $ Revision 1.1.1.1 2003/10/18 13:08:21 nnick Common
 * classes for trainer
 * 
 * Revision 1.2 2003/10/12 14:38:53 nnick dialogs, reporter and DumpSwing are
 * now in packages
 * 
 * Revision 1.1 2003/10/11 06:21:54 nnick Added interface ISelfDumped to dump
 * formulas also
 * 
 * This interface is to make dumping possible (using DumpSwing) for formulas
 */
public interface ISelfDumped {

    public String asHTML();
}
