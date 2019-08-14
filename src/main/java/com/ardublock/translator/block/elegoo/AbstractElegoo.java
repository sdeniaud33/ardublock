// Not now used by standard blocks. Retained as long as it is referenced by legacy blocks.
package com.ardublock.translator.block.elegoo;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public abstract class AbstractElegoo extends TranslatorBlock {

	protected enum PinMode {
		not_set, input, pull_up, output
	}

	protected enum ArgType {
		number, other
	}

	protected class ArgValueResult {
		ArgType argType;
		String value;
	}

	protected String controlDisplayName;

	public AbstractElegoo(String controlDisplayName, Long blockId, Translator translator, String codePrefix,
			String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
		this.controlDisplayName = controlDisplayName;
	}

	protected ArgValueResult getArgValue(int argNumber)
			throws BlockException, SocketNullException, SubroutineNotDeclaredException {
		return this.getArgValue(argNumber, PinMode.not_set);
	}

	protected ArgValueResult getArgValue(int argNumber, PinMode digitalType)
			throws BlockException, SocketNullException, SubroutineNotDeclaredException {
		ArgValueResult result = new ArgValueResult();
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(argNumber);
		String val = translatorBlock.toCode();
		result.value = val;
		if (translatorBlock instanceof NumberBlock) {
			result.argType = ArgType.number;
			try {
				int pinNumber = Integer.parseInt(val);
				if ((pinNumber < 0) || (pinNumber > 53))
					throw new BlockException(blockId,
							controlDisplayName + " : le numéro de la broche doit être entre 0 et 53");
				switch (digitalType) {
				case pull_up:
					translator.addPullupDigitalPin(pinNumber);
					break;
				case input:
					translator.addInputDigitalPin(pinNumber);
					break;
				case output:
					translator.addOutputDigitalPin(pinNumber);
					break;
				default:
					break;
				}
			} catch (NumberFormatException e) {
				// Nothing to do : simply not an integer : maybe a variable name or sth like A0
			}
		} else {
			// Probably a variable
			result.argType = ArgType.other;
		}

		return result;
	}

	protected String addDigitalWrite(int pinArgNumber, int pinValNumber)
			throws BlockException, SocketNullException, SubroutineNotDeclaredException {
		ArgValueResult pinArg = getArgValue(pinArgNumber, PinMode.output);
		if (pinArg.argType == ArgType.number) {
			TranslatorBlock translatorBlock2 = this.getRequiredTranslatorBlockAtSocket(pinValNumber);
			return "digitalWrite(" + pinArg.value + ", " + translatorBlock2.toCode() + ");\n";
		} else {
			TranslatorBlock translatorBlock2 = this.getRequiredTranslatorBlockAtSocket(pinValNumber);
			// We need to use a function to make sure the pinMode will be set
			translator.addDefinitionCommand(
					"void _internal_digitalWrite(int pinNumber, boolean status)\n{\npinMode(pinNumber, OUTPUT);\ndigitalWrite(pinNumber, status);\n}\n");
			return "_internal_digitalWrite(" + pinArg.value + ", " + translatorBlock2.toCode() + ");\n";
		}
	}

	protected String addDigitalRead(int pinArgNumber, boolean withPullUp)
			throws BlockException, SocketNullException, SubroutineNotDeclaredException {
		ArgValueResult pinArg = getArgValue(pinArgNumber, withPullUp ? PinMode.pull_up : PinMode.input);
		if (pinArg.argType == ArgType.number) {
			if (withPullUp) 
				return "digitalRead(" + pinArg.value + ") == LOW";
			else
				return "digitalRead(" + pinArg.value + ")";
		} else {
			// We need to use a function to make sure the pinMode will be set
			if (withPullUp) {
				translator.addDefinitionCommand(
						"int _internal_digitalReadPullUp(int pinNumber)\n{\npinMode(pinNumber, INPUT_PULLUP);\nreturn digitalRead(pinNumber);\n}\n");
				if (withPullUp) 
					return "_internal_digitalReadPullUp(" + pinArg.value + ") == LOW";
				else
					return "_internal_digitalReadPullUp(" + pinArg.value + ")";
			} else {
				translator.addDefinitionCommand(
						"int _internal_digitalRead(int pinNumber, boolean status)\n{\npinMode(pinNumber, OUTPUT);\nreturn digitalRead(pinNumber);\n}\n");
				return "_internal_digitalRead(" + pinArg.value + ")";
			}
		}
	}

}
