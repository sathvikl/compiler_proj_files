	public Object visitDoEachCommand(DoEachCommand doEachCommand, Object arg)
			throws Exception {
		doEachCommand.lValue.visit(this, arg);
		
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/HashMap", "entrySet", "()Ljava/util/Set;");
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "iterator", "()Ljava/util/Iterator;");
		Label endOfDoLoop = new Label();
		mv.visitJumpInsn(GOTO, endOfDoLoop);
		
		Label startDoLoop = new Label();
		mv.visitLabel(startDoLoop);
		mv.visitInsn(DUP);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;");
		mv.visitTypeInsn(CHECKCAST, "java/util/Map$Entry");
		
		mv.visitInsn(DUP);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map$Entry", "getKey", "()Ljava/lang/Object;");
		//mv.visitInsn(POP);
		
		unBox( ((CompoundType)((SimpleLValue)doEachCommand.lValue).type).keyType );
		String key_symbol_name = doEachCommand.key.getText();
		key_symbol_name = (symbol_table.lookup(key_symbol_name)).scope_number + key_symbol_name;
		switch( (((CompoundType)((SimpleLValue)doEachCommand.lValue).type).keyType).type ) {
		case INT:	
			mv.visitFieldInsn(PUTSTATIC, className, key_symbol_name, "I");
			break;
		case BOOLEAN:
			mv.visitFieldInsn(PUTSTATIC, className, key_symbol_name, "Z");
			break;
		case STRING:
			mv.visitFieldInsn(PUTSTATIC, className, key_symbol_name, "Ljava/lang/String;");
			break;	
		}
		
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map$Entry", "getValue", "()Ljava/lang/Object;");
		//mv.visitInsn(POP);
		
		unBox( ((CompoundType)((SimpleLValue)doEachCommand.lValue).type).valType);
		String val_symbol_name = doEachCommand.val.getText();
		val_symbol_name = (symbol_table.lookup(val_symbol_name)).scope_number + val_symbol_name;
		switch( ((SimpleType)((CompoundType)((SimpleLValue)doEachCommand.lValue).type).valType).type ) {
		case INT:	
			mv.visitFieldInsn(PUTSTATIC, className, val_symbol_name, "I");
			break;
		case BOOLEAN:
			mv.visitFieldInsn(PUTSTATIC, className, val_symbol_name, "Z");
			break;
		case STRING:
			mv.visitFieldInsn(PUTSTATIC, className, val_symbol_name, "Ljava/lang/String;");
			break;	
		}
		
		doEachCommand.block.visit(this, arg);
		mv.visitLabel(endOfDoLoop);
		mv.visitInsn(DUP);
		mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z");
		mv.visitJumpInsn(IFNE, startDoLoop);
		mv.visitInsn(POP);
		return null;
	}