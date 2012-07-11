@Override
	public Object visitPair(Pair pair, Object arg) throws Exception {
		pair.expression0.visit(this, arg);
		if (pair.expression0.type.isEqualTo(inT))
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
		else if (pair.expression0.type.isEqualTo(bool))
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf",
					"(Z)Ljava/lang/Boolean;");
		pair.expression1.visit(this, arg);
		if (pair.expression1.type.isEqualTo(inT))
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf",
					"(I)Ljava/lang/Integer;");
		else if (pair.expression1.type.isEqualTo(bool))
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf",
					"(Z)Ljava/lang/Boolean;");
		return null;
	}
@Override
	public Object visitPairList(PairList pairList, Object arg) throws Exception {
		for (Pair pair : pairList.pairs) {
			mv.visitFieldInsn(GETSTATIC, className, "TempMap" + mapCount,
					"Ljava/util/HashMap;");
			pair.visit(this, arg);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/HashMap", "put",
					"(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			mv.visitInsn(POP);
		}
		return null;
	}
@Override
	public Object visitAssignPairListCommand(
			AssignPairListCommand assignPairListCommand, Object arg)
			throws Exception {
		String var = (String) assignPairListCommand.lValue.visit(this, arg);
		CompoundType compoundType = (CompoundType) assignPairListCommand.lValue.type;
		String type = getMapSignature(compoundType);
		fv = cw.visitField(ACC_STATIC, "TempMap" + mapCount,
				"Ljava/util/HashMap;", type, null);
		fv.visitEnd();
		mv.visitTypeInsn(NEW, "java/util/HashMap");
		mv.visitInsn(DUP);
		mv.visitMethodInsn(INVOKESPECIAL, "java/util/HashMap", "<init>", "()V");
		mv.visitFieldInsn(PUTSTATIC, className, "TempMap" + mapCount,
				"Ljava/util/HashMap;");
		assignPairListCommand.pairList.visit(this, arg);
		mv.visitFieldInsn(GETSTATIC, className, var, "Ljava/util/HashMap;");
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/HashMap", "clear", "()V");
		mv.visitFieldInsn(GETSTATIC, className, var, "Ljava/util/HashMap;");
		mv.visitFieldInsn(GETSTATIC, className, "TempMap" + mapCount,
				"Ljava/util/HashMap;");
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/HashMap", "putAll",
				"(Ljava/util/Map;)V");
		mapCount++;
		return null;
	}
